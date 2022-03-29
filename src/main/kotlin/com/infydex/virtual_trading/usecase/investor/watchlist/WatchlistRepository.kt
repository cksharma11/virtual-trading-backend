package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

interface WatchlistRepository : JpaRepository<WatchlistEntity, Any> {
    fun findAllByInvestorId(investorId: Int): Optional<List<WatchlistEntity>>

    @Modifying
    @Query(value = "insert into watchlist (investor_id, stock) VALUES (:investorId, :stock)", nativeQuery = true)
    @Transactional
    fun addStock(investorId: Int, stock: String)

    fun deleteByInvestorIdAndStock(investorId: Int, stock: String): Int
}
