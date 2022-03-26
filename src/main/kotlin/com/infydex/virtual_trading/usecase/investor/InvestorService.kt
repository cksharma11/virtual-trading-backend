package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.usecase.investor.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.entity.InvestorEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class InvestorService(
    private val investorRepository: InvestorRepository,
) {
    @Transactional
    fun signup(investorSignupDto: InvestorSignupDto): InvestorEntity {
        try {
            return investorRepository.save(InvestorEntity().copy(phone = investorSignupDto.phone))
        } catch (ex: Exception) {
            throw PhoneNumberAlreadyRegisteredException()
        }
    }
}
