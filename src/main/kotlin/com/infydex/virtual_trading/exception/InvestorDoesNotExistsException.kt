package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class InvestorDoesNotExistsException(message: String = ErrorMessage.INVESTOR_DOES_NOT_EXISTS) :
    RuntimeException(message)
