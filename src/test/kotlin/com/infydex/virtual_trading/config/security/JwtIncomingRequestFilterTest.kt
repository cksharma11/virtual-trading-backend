package com.infydex.virtual_trading.config.security

import com.infydex.virtual_trading.config.security.JwtIncomingRequestFilter.Companion.X_JWT_PAYLOAD
import mu.KLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@ExtendWith(SpringExtension::class)
@WebMvcTest(JWTFilterTestController::class, PrincipalProvider::class, WebSecurityConfiguration::class)
internal class JwtIncomingRequestFilterTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return 403 if call is not authenticated`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/auth-test-endpoint")
                .contextPath("/virtual-trading")
                .contentType("application/json")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun `should return 200 if call is authenticated`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/auth-test-endpoint")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(username = "test-user")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .string("""{"username":"test-user","isAuthenticated":true}""")
            )
    }

    @Test
    fun `should return 400 bad request if provided broken jwt`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/auth-test-endpoint")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    X_JWT_PAYLOAD,
                    "{\"user\": \"test\""
                )
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}

@RestController
class JWTFilterTestController {
    companion object : KLogging()

    @Autowired
    lateinit var principalProvider: PrincipalProvider

    @GetMapping("api/auth-test-endpoint", produces = ["application/json"])
    fun testEndpoint(): Map<String, Any> {
        return mapOf(
            "username" to principalProvider.userName(),
            "isAuthenticated" to principalProvider.isAuthenticated(),
        )
    }
}
