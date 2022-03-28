package com.infydex.virtual_trading.usecase.investor.onboarding

import com.fasterxml.jackson.databind.ObjectMapper
import com.infydex.virtual_trading.exception.InvalidInvestorIdException
import com.infydex.virtual_trading.exception.InvalidLoginCredentialsException
import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.exception.handler.VirtualTradingExceptionHandler
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorLoginDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.PinDto
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.PinEntity
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
internal class InvestorControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var investorService: InvestorService

    @InjectMocks
    lateinit var investorController: InvestorController

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(investorController)
            .setControllerAdvice(VirtualTradingExceptionHandler())
            .build()
    }

    @Test
    fun `should create new investor`() {
        val investor = InvestorSignupDto(phone = "9876543210")

        given(investorService.signup(investor)).willReturn(InvestorEntity().copy(phone = investor.phone))

        mockMvc.post("/api/v1/investor/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().createObjectNode().put("phone", "9876543210").toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isCreated()
                content { json("{\"phone\":\"9876543210\"}", false) }
            }
        }

        verify(investorService).signup(investor)
    }

    @Test
    fun `should throw phone number already exists exception when registering twice with same number`() {
        val investor = InvestorSignupDto(phone = "9876543210")

        given(investorService.signup(investor)).willThrow(PhoneNumberAlreadyRegisteredException())

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

    @Test
    fun `should return 400 bad request when phone number is invalid`() {
        mockMvc.post("/api/v1/investor/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().createObjectNode().put("phone", "1234567").toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
                content { json("{\"phone\":\"Invalid phone number\"}") }
            }
        }
    }

    @Test
    fun `should create login pin when provided valid 4 digit pin`() {
        val createPinDtoString = ObjectMapper().createObjectNode()
            .put("pin", "1234")
            .put("investorId", 1)
            .toString()

        val pinDto = PinDto(investorId = 1, pin = "1234")

        given(investorService.createPin(pinDto))
            .willReturn(PinEntity().copy(pin = "1234", investorId = 1))

        mockMvc.post("/api/v1/investor/create-pin") {
            contentType = MediaType.APPLICATION_JSON
            content = createPinDtoString
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isCreated()
                content { json("{\"pin\":\"1234\",\"investorId\": 1}") }
            }
        }
    }

    @Test
    fun `should return 400 bad request when provided pin with length more than 4`() {
        val createPinDtoString = ObjectMapper().createObjectNode()
            .put("pin", "123456")
            .put("investorId", "1")
            .toString()

        val pinDto = PinDto(investorId = 1, pin = "1234")

        given(investorService.createPin(pinDto))
            .willReturn(PinEntity().copy(pin = "1234", investorId = 1))

        mockMvc.post("/api/v1/investor/create-pin") {
            contentType = MediaType.APPLICATION_JSON
            content = createPinDtoString
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
            }
        }
    }

    @Test
    fun `should return 400 bad request when provided pin with length less than 4`() {
        val createPinDtoString = ObjectMapper().createObjectNode()
            .put("pin", "123")
            .put("investorId", "1")
            .toString()

        val pinDto = PinDto(investorId = 1, pin = "1234")

        given(investorService.createPin(pinDto))
            .willReturn(PinEntity().copy(pin = "1234", investorId = 1))

        mockMvc.post("/api/v1/investor/create-pin") {
            contentType = MediaType.APPLICATION_JSON
            content = createPinDtoString
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
            }
        }
    }

    @Test
    fun `should throw exception when investor id foreign key constraint violates`() {
        val createPinDtoPayload = ObjectMapper().createObjectNode()
            .put("pin", "1234")
            .put("investorId", "1234")
            .toString()

        val pinDto = PinDto(investorId = 1234, pin = "1234")

        doThrow(InvalidInvestorIdException()).`when`(investorService).createPin(pinDto)

        mockMvc.post("/api/v1/investor/create-pin") {
            contentType = MediaType.APPLICATION_JSON
            content = createPinDtoPayload
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
                content { json("{\"status\":400,\"message\":\"Invalid investor id provided\",\"errorType\":\"\"}") }
            }
        }
    }

    @Test
    fun `should return customer with jwt token on valid login`() {
        val investorLoginDtoPayload = ObjectMapper().createObjectNode()
            .put("pin", "1234")
            .put("investorId", 1)
            .toString()
        val investorLoginDto = InvestorLoginDto(investorId = 1, pin = "1234")

        given(investorService.login(investorLoginDto))
            .willReturn(PinEntity().copy(investorId = 1, pin = "1234"))

        given(investorService.getInvestorById(1))
            .willReturn(InvestorEntity().copy(phone = "9876543210", id = 1))

        mockMvc.post("/api/v1/investor/login") {
            contentType = MediaType.APPLICATION_JSON
            content = investorLoginDtoPayload
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isOk()
                content {
                    json(
                        // TODO: Add jwt mock and replace the actual token with mocked one
                        "{\"investor\": {\"phone\": \"9876543210\", \"id\": 1}, \"jwt\": \"eyJzdWIiOjEsInR5cCI6IkpXVCIsInVzZXIiOnsiZW50aXRsZW1lbnRzIjpbImluZnlkZXguaW52ZXN0b3IiXSwicGhvbmUiOlsiOTg3NjU0MzIxMCJdfSwiYWxnIjoiSFMyNTYifQ.eyJpc3MiOiJhdXRoMCJ9.1768-3L0-N74-IwdokUg84nKJrswjz2AKH-Hb_5bFJU\"}",
                        false
                    )
                }
            }
        }
    }

    @Test
    fun `should return 401 unauthorised when provided incorrect login details`() {
        val investorLoginDtoPayload = ObjectMapper().createObjectNode()
            .put("pin", "1111")
            .put("investorId", 1)
            .toString()
        val investorLoginDto = InvestorLoginDto(investorId = 1, pin = "1111")

        given(investorService.login(investorLoginDto))
            .willThrow(InvalidLoginCredentialsException())

        given(investorService.getInvestorById(1))
            .willReturn(InvestorEntity().copy(phone = "9876543210", id = 1))

        mockMvc.post("/api/v1/investor/login") {
            contentType = MediaType.APPLICATION_JSON
            content = investorLoginDtoPayload
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isUnauthorized()
                content {
                    json("{\"status\":401,\"message\":\"Invalid login credentials\",\"errorType\":\"\"}")
                }
            }
        }
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(investorService)
    }
}
