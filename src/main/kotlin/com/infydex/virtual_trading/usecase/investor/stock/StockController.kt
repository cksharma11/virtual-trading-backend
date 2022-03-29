package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/stock")
class StockController {
    @Autowired
    lateinit var stockService: StockService

    @PostMapping("/buy")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun buy(@Valid @RequestBody stockTransactionDto: StockTransactionDto, request: HttpServletRequest): StockEntity {
        val investorId = request.userPrincipal.name
        return stockService.buy(investorId.toInt(), stockTransactionDto)
    }
}
