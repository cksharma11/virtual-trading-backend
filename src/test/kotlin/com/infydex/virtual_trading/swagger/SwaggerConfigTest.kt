package com.infydex.virtual_trading.swagger

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner::class)
internal class SwaggerConfigTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun virtualTradingSwagger() {
        mockMvc.get("/swagger-ui/index.html")
            .andExpect { status { isOk() } }
    }
}
