package com.infydex.virtual_trading.usecase.investor.stock.dto

import javax.validation.constraints.NotNull

data class StockTransactionDto(
    @NotNull
    val stockSymbol: String,

    @NotNull
    val price: Double,

    @NotNull
    val quantity: Int,
)

enum class StockTransactionType {
    BUY, SELL
}
