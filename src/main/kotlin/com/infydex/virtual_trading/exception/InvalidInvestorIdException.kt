package com.infydex.virtual_trading.exception

import com.infydex.virtual_trading.exception.message.ErrorMessage

class InvalidInvestorIdException(message: String = ErrorMessage.INVALID_INVESTOR_ID) :
    RuntimeException(message)
