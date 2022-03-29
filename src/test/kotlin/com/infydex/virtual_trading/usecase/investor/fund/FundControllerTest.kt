package com.infydex.virtual_trading.usecase.investor.fund

import com.fasterxml.jackson.databind.ObjectMapper
import com.infydex.virtual_trading.config.security.JwtIncomingRequestFilter.Companion.X_JWT_PAYLOAD
import com.infydex.virtual_trading.config.security.TestAuthUtils
import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.dto.FundResponseDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.nhaarman.mockito_kotlin.times
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
internal class FundControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var fundService: FundService

    @Test
    fun `should add fund when provided valid fund request`() {
        val addFundDto = AddFundDto(amount = 200.0)
        val addAmountPayload = ObjectMapper().createObjectNode().put("amount", "200").toString()

        BDDMockito.given(fundService.createFundEntry(1, addFundDto, TransactionType.CREDIT))
            .willReturn(FundEntity().copy(amount = 200.0, investorId = 1))

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/fund/add")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAmountPayload)
                .header(
                    X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(fundService, times(1)).createFundEntry(1, addFundDto, TransactionType.CREDIT)
    }

    @Test
    fun `should return available fund for investor`() {
        BDDMockito.given(fundService.getAvailableFund(1))
            .willReturn(FundResponseDto(fund = 1000.0))

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/v1/fund/")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"fund\":1000.0}"))

        Mockito.verify(fundService, times(1)).getAvailableFund(1)
    }
}
