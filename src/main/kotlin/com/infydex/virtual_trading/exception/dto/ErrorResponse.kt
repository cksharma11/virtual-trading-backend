package com.infydex.virtual_trading.exception.dto

data class ErrorResponse(
    val status: Int,
    val message: String = "",
    val errorType: String = ""
)
