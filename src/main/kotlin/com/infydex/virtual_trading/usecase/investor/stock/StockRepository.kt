package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<StockEntity, Any>
