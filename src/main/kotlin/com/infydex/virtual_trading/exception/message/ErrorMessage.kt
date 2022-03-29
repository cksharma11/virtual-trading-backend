package com.infydex.virtual_trading.exception.message

object ErrorMessage {
    val INSUFFICIENT_FUND = "Insufficient fund"
    val INTERNAL_SERVER_ERROR = "Internal server error"
    val INVESTOR_DOES_NOT_EXISTS = "Investor does not exists with given investor id"
    val INVALID_LOGIN_CREDENTIALS = "Invalid login credentials"
    val INVALID_INVESTOR_ID = "Invalid investor id provided"
    val INVESTOR_ALREADY_REGISTRED = "Investor already registered with same phone number"
}
