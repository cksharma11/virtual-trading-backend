package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class PhoneNumberAlreadyRegisteredException(message: String = ErrorMessage.INVESTOR_ALREADY_REGISTRED) :
    RuntimeException(message)
