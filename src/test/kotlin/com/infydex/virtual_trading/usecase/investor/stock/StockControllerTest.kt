package com.infydex.virtual_trading.usecase.investor.stock

import com.fasterxml.jackson.databind.ObjectMapper
import com.infydex.virtual_trading.config.security.JwtIncomingRequestFilter
import com.infydex.virtual_trading.config.security.TestAuthUtils
import com.infydex.virtual_trading.exception.InsufficientFundException
import com.infydex.virtual_trading.exception.InsufficientHoldingException
import com.infydex.virtual_trading.usecase.investor.stock.dto.HoldingResponseDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
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
internal class StockControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var stockService: StockService

    @Test
    fun `should place buy order if funds are sufficient`() {
        val buyTransactionDto = ObjectMapper().createObjectNode()
            .put("stockSymbol", "WIPRO")
            .put("price", 100.0)
            .put("quantity", 10)
            .toString()

        val stockTransaction = StockTransactionDto(
            stockSymbol = "WIPRO",
            quantity = 10,
            price = 100.0
        )

        BDDMockito.given(stockService.buy(1, stockTransaction))
            .willReturn(StockEntity().copy(stockSymbol = "WIPRO"))

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/stock/buy")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buyTransactionDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should throw InsufficientFund Exception when funds are lower than stock costs`() {
        val buyTransactionDto = ObjectMapper().createObjectNode()
            .put("stockSymbol", "WIPRO")
            .put("price", 100.0)
            .put("quantity", 10)
            .toString()

        val stockTransaction = StockTransactionDto(
            stockSymbol = "WIPRO",
            quantity = 10,
            price = 100.0
        )

        BDDMockito.given(stockService.buy(1, stockTransaction))
            .willThrow(InsufficientFundException())

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/stock/buy")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buyTransactionDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("{\"status\":400,\"message\":\"Insufficient fund\",\"errorType\":\"\"}")
            )
    }

    @Test
    fun `should place sell order when have stocks for sell`() {
        val buyTransactionDto = ObjectMapper().createObjectNode()
            .put("stockSymbol", "WIPRO")
            .put("price", 100.0)
            .put("quantity", 10)
            .toString()

        val stockTransaction = StockTransactionDto(
            stockSymbol = "WIPRO",
            quantity = 10,
            price = 100.0
        )

        BDDMockito.given(stockService.sell(1, stockTransaction))
            .willReturn(StockEntity().copy(stockSymbol = "WIPRO"))

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/stock/sell")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buyTransactionDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should throw Insufficient holding exception if not have holdings for sell`() {
        val buyTransactionDto = ObjectMapper().createObjectNode()
            .put("stockSymbol", "WIPRO")
            .put("price", 100.0)
            .put("quantity", 10)
            .toString()

        val stockTransaction = StockTransactionDto(
            stockSymbol = "WIPRO",
            quantity = 10,
            price = 100.0
        )

        BDDMockito.given(stockService.sell(1, stockTransaction))
            .willThrow(InsufficientHoldingException())

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/stock/sell")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buyTransactionDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("{\"status\":400,\"message\":\"Insufficient holding\",\"errorType\":\"\"}")
            )
    }

    @Test
    fun `should return all holdings of investor`() {
        val holdings = listOf(
            HoldingResponseDto(
                stockSymbol = "WIPRO",
                cost = 1000.0,
                averagePrice = 100.0,
                quantity = 10
            )
        )

        BDDMockito.given(stockService.holdings(1))
            .willReturn(holdings)

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/v1/stock/holdings")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("[{\"stockSymbol\":\"WIPRO\",\"quantity\":10,\"averagePrice\":100.0,\"cost\":1000.0}]")
            )
    }

    @Test
    fun `should return all transaction of investor`() {
        val transactions = listOf(
            StockEntity().copy(stockSymbol = "WIPRO", price = 10.0, quantity = 10)
        )

        BDDMockito.given(stockService.transactions(1))
            .willReturn(transactions)

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/v1/stock/transactions")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("[{\"investorId\":1,\"type\":\"BUY\",\"stockSymbol\":\"WIPRO\",\"price\":10.0,\"quantity\":10,\"status\":\"COMPLETED\"}]")
            )
    }
}
