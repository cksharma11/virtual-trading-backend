package com.infydex.virtual_trading.usecase.investor.watchlist.dto

import javax.validation.constraints.NotNull

data class AddStockDto(
    @NotNull
    val stock: String
)
