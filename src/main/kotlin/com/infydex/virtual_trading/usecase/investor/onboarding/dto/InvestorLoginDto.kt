package com.infydex.virtual_trading.usecase.investor.onboarding.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

data class InvestorLoginDto(
    @NotNull
    @field:Length(min = 4, max = 4)
    val pin: String,

    @NotNull
    val investorId: Int
)
