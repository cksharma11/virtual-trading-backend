package com.infydex.virtual_trading.usecase.investor.onboarding

import com.infydex.virtual_trading.exception.InvalidInvestorIdException
import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorLoginDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.PinDto
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.PinEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class InvestorService(
    private val investorRepository: InvestorRepository,
    private val pinRepository: PinRepository,
) {
    @Transactional
    fun signup(investorSignupDto: InvestorSignupDto): InvestorEntity {
        try {
            return investorRepository.save(InvestorEntity().copy(phone = investorSignupDto.phone))
        } catch (ex: Exception) {
            throw PhoneNumberAlreadyRegisteredException()
        }
    }

    @Transactional
    fun createPin(pinDto: PinDto): PinEntity {
        try {
            return pinRepository.save(PinEntity().copy(pin = pinDto.pin, investorId = pinDto.investorId))
        } catch (ex: Exception) {
            throw InvalidInvestorIdException()
        }
    }

    fun login(investorLoginDto: InvestorLoginDto): PinEntity? {
        return pinRepository.findByInvestorIdAndPin(investorLoginDto.investorId, investorLoginDto.pin)
    }

    fun getInvestorById(investorId: Int): InvestorEntity {
        return investorRepository.findById(investorId).get()
    }
}
