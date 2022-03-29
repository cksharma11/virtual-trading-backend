package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class InsufficientHoldingException(message: String = ErrorMessage.INSUFFICIENT_HOLDING) :
    RuntimeException(message)
