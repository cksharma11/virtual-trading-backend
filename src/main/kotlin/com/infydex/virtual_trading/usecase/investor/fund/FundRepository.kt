package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FundRepository : JpaRepository<FundEntity, Int>
