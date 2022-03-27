package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.usecase.investor.entity.PinEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PinRepository : JpaRepository<PinEntity, Int>
