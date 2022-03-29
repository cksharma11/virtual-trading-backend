package com.infydex.virtual_trading.usecase.investor.watchlist

import com.fasterxml.jackson.databind.ObjectMapper
import com.infydex.virtual_trading.config.security.JwtIncomingRequestFilter
import com.infydex.virtual_trading.config.security.TestAuthUtils
import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
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
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
internal class WatchlistControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var watchlistService: WatchlistService

    @Test
    fun `should return watchlist for investor`() {
        BDDMockito.given(watchlistService.getWatchlist(1))
            .willReturn(Optional.of(listOf(WatchlistEntity().copy(stock = "WIPRO"))))

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.GET, "/virtual-trading/api/v1/watchlist/")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[\"WIPRO\"]"))
    }

    @Test
    fun `should add stock to watchlist`() {
        val addStockDto = ObjectMapper().createObjectNode()
            .put("stock", "WIPRO")
            .toString()

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/watchlist/add-stock")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addStockDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should remove stock from watchlist`() {
        val addStockDto = ObjectMapper().createObjectNode()
            .put("stock", "WIPRO")
            .toString()

        BDDMockito.given(watchlistService.removeStock(1, "WIPRO"))
            .willReturn(1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .request(HttpMethod.POST, "/virtual-trading/api/v1/watchlist/remove-stock")
                .contextPath("/virtual-trading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addStockDto)
                .header(
                    JwtIncomingRequestFilter.X_JWT_PAYLOAD,
                    TestAuthUtils.createJWTPayload(userId = "1")
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"status\":1}"))
    }
}
