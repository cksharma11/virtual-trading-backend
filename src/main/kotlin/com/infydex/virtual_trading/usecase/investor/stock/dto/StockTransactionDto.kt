package com.infydex.virtual_trading.usecase.investor.stock.dto

import javax.validation.constraints.NotNull

data class BuyStockDto(
    @NotNull
    val type: StockTransactionType,

    @NotNull
    val investorId: Int,

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
