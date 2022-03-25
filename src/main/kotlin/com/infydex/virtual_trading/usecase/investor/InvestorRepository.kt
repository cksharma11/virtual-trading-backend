package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.usecase.investor.entity.InvestorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InvestorRepository : JpaRepository<InvestorEntity, Int>
