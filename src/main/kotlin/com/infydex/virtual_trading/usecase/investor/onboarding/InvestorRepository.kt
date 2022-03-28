package com.infydex.virtual_trading.usecase.investor.onboarding

import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InvestorRepository : JpaRepository<InvestorEntity, Int>
