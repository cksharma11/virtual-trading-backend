package com.infydex.virtual_trading.usecase.investor.fund.dto

import javax.validation.constraints.NotNull

data class AddFundDto(
    @NotNull
    val amount: Double,
)
