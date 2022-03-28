package com.infydex.virtual_trading.usecase.investor.onboarding

import com.infydex.virtual_trading.usecase.investor.onboarding.entity.PinEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PinRepository : JpaRepository<PinEntity, Int> {
    fun findByInvestorIdAndPin(investorId: Int, pin: String): PinEntity?
}
