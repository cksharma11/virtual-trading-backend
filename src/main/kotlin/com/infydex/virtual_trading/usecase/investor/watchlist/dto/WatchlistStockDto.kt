package com.infydex.virtual_trading.usecase.investor.watchlist.dto

import javax.validation.constraints.NotNull

data class WatchlistStockDto(
    @NotNull
    val stock: String
)
