package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class WatchlistService(private val watchlistRepository: WatchlistRepository) {
    fun getWatchlist(investorId: Int): Optional<List<WatchlistEntity>> {
        return watchlistRepository.findAllByInvestorId(investorId)
    }

    @Transactional
    fun addStock(investorId: Int, stock: String) {
        return watchlistRepository.addStock(investorId, stock)
    }

    @Transactional
    fun removeStock(investorId: Int, stock: String): Int {
        return watchlistRepository.deleteByInvestorIdAndStock(investorId, stock)
    }
}
