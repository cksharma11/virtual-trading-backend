package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.usecase.investor.dto.InvestorSignupDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/investor")
class InvestorController {
    @Autowired
    lateinit var investorService: InvestorService

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody investorSignupDto: InvestorSignupDto): Any {
        return investorService.signup(investorSignupDto)
    }
}
