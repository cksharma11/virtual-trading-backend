package com.infydex.virtual_trading.usecase.investor.watchlist

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

internal class WatchlistServiceTest {
    @Mock
    lateinit var watchlistRepository: WatchlistRepository

    @InjectMocks
    lateinit var watchlistService: WatchlistService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should return watchlist of investor`() {
        given(watchlistRepository.findAllByInvestorId(1))
            .willReturn(Optional.of(listOf()))

        watchlistService.getWatchlist(1)

        verify(watchlistRepository, times(1)).findAllByInvestorId(1)
    }

    @Test
    fun `should add stock to watchlist`() {
        doNothing().`when`(watchlistRepository).addStock(1, "WIPRO")

        watchlistService.getWatchlist(1)

        verify(watchlistRepository, times(1)).findAllByInvestorId(1)
    }

    @Test
    fun `should remove stock from watchlist table`() {
        given(watchlistRepository.deleteByInvestorIdAndStock(1, "WIPRO"))
            .willReturn(1)

        watchlistService.removeStock(1, "WIPRO")

        verify(watchlistRepository, times(1)).deleteByInvestorIdAndStock(1, "WIPRO")
    }
}
