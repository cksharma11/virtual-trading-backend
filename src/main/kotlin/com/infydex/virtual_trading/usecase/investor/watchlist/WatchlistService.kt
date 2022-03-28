package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class WatchlistService(private val watchlistRepository: WatchlistRepository) {
    fun getWatchlist(investorId: Int): Optional<List<WatchlistEntity>> {
        return watchlistRepository.findAllByInvestorId(investorId)
    }
}
