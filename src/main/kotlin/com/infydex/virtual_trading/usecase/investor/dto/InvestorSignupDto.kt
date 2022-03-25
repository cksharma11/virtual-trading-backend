package com.infydex.virtual_trading.usecase.investor.dto

import javax.validation.constraints.Size

data class InvestorSignupDto(
    @Size(min = 10, max = 10)
    val phone: String
)
