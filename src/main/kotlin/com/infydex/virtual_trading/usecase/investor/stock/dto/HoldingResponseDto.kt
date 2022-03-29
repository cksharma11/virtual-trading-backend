package com.infydex.virtual_trading.usecase.investor.stock.dto

data class HoldingResponseDto(
    val stockSymbol: String,
    val quantity: Int,
    val averagePrice: Double,
    val cost: Double
)
