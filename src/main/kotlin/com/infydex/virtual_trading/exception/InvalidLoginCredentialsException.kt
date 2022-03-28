package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class InvalidLoginCredentialsException(message: String = ErrorMessage.INVALID_LOGIN_CREDENTIALS) :
    RuntimeException(message)
