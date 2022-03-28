package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WatchlistRepository : JpaRepository<WatchlistEntity, Any> {
    fun findAllByInvestorId(investorId: Int): Optional<List<WatchlistEntity>>
}
