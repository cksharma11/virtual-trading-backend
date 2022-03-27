package com.infydex.virtual_trading.usecase.investor.dto

import com.infydex.virtual_trading.constraints.phone.ValidPhone

data class InvestorSignupDto(
    @ValidPhone
    val phone: String
)
