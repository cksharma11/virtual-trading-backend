package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class InsufficientFundException(message: String = ErrorMessage.INSUFFICIENT_FUND) :
    RuntimeException(message)
