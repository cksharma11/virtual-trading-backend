package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.dto.FundResponseDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/fund")
class FundController {
    @Autowired
    lateinit var fundService: FundService

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun addFund(@Valid @RequestBody addFundDto: AddFundDto, request: HttpServletRequest): FundEntity {
        val investorId = request.userPrincipal.name
        return fundService.createFundEntry(investorId.toInt(), addFundDto, TransactionType.CREDIT)
    }

    @GetMapping("/")
    fun getFund(request: HttpServletRequest): FundResponseDto {
        val investorId = request.userPrincipal.name
        return fundService.getAvailableFund(investorId.toInt())
    }
}
