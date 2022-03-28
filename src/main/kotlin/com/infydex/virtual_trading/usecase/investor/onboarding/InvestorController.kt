package com.infydex.virtual_trading.usecase.investor.onboarding

import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.PinDto
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.PinEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/investor")
class InvestorController {
    @Autowired
    lateinit var investorService: InvestorService

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun signup(@Valid @RequestBody investorSignupDto: InvestorSignupDto): InvestorEntity {
        return investorService.signup(investorSignupDto)
    }

    @PostMapping("/create-pin")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createPin(@Valid @RequestBody pinDto: PinDto): PinEntity {
        return investorService.createPin(pinDto)
    }
}
