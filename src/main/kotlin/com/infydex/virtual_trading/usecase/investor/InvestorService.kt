package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.usecase.investor.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.entity.InvestorEntity
import org.springframework.stereotype.Service

@Service
class InvestorService(
    private val investorRepository: InvestorRepository,
) {
    fun signup(investorSignupDto: InvestorSignupDto): InvestorEntity {
        return investorRepository.save(
            InvestorEntity().copy(phone = investorSignupDto.phone)
        )
    }
}
