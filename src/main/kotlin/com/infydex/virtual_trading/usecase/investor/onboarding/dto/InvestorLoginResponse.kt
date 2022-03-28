package com.infydex.virtual_trading.usecase.investor.onboarding.dto

import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import java.util.*

data class InvestorLoginResponse(
    val investor: InvestorEntity,
    val jwt: String
)
