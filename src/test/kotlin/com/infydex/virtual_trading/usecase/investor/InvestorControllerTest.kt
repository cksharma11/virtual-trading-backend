package com.infydex.virtual_trading.usecase.investor

import com.fasterxml.jackson.databind.ObjectMapper
import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.usecase.investor.dto.InvestorSignupDto
import org.junit.After
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
internal class InvestorControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var investorService: InvestorService

    @Test
    fun `should create new investor`() {
        val investor = InvestorSignupDto(phone = "9876543210")

        mockMvc.post("/api/v1/investor/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().createObjectNode().put("phone", "9876543210").toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

        BDDMockito.verify(investorService).signup(investor)
    }

    @Test
    fun `should throw phone number already exists exception when registering twice with same number`() {
        val investor = InvestorSignupDto(phone = "9876543210")

        BDDMockito.given(investorService.signup(investor)).willThrow(PhoneNumberAlreadyRegisteredException())

        mockMvc.post("/api/v1/investor/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().createObjectNode().put("phone", "9876543210").toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isConflict()
                content {
                    json(
                        "{\n" +
                            "    \"status\": 409,\n" +
                            "    \"message\": \"Investor already registered with same phone number\",\n" +
                            "    \"errorType\": \"\"\n" +
                            "}"
                    )
                }
            }
        }
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(investorService)
    }
}
