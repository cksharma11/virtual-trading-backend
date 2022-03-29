package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class WatchlistService(private val watchlistRepository: WatchlistRepository) {
    fun getWatchlist(investorId: Int): Optional<List<WatchlistEntity>> {
        return watchlistRepository.findAllByInvestorId(investorId)
    }

    fun addStock(investorId: Int, stock: String) {
        return watchlistRepository.addStock(investorId, stock)
    }

    fun removeStock(investorId: Int, stock: String): Int {
        return watchlistRepository.deleteByInvestorIdAndStock(investorId, stock)
    }
}
