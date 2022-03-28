package com.infydex.virtual_trading.usecase.investor.onboarding.dto

import com.infydex.virtual_trading.constraints.phone.ValidPhone

data class InvestorSignupDto(
    @ValidPhone
    val phone: String
)
