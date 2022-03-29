package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.dto.RemoveStockResponse
import com.infydex.virtual_trading.usecase.investor.watchlist.dto.WatchlistStockDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/watchlist")
class WatchlistController {
    @Autowired
    lateinit var watchlistService: WatchlistService

    @GetMapping("/")
    fun getWatchlist(request: HttpServletRequest): List<String> {
        val investorId = request.userPrincipal.name
        return watchlistService
            .getWatchlist(investorId.toInt()).get()
            .map { it.stock }
    }

    @PostMapping("/add-stock")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun addStock(@Valid @RequestBody watchlistStockDto: WatchlistStockDto, request: HttpServletRequest) {
        val investorId = request.userPrincipal.name
        return watchlistService.addStock(investorId.toInt(), watchlistStockDto.stock)
    }

    @PostMapping("/remove-stock")
    fun removeStock(@Valid @RequestBody watchlistStockDto: WatchlistStockDto, request: HttpServletRequest): RemoveStockResponse {
        val investorId = request.userPrincipal.name
        return RemoveStockResponse(status = watchlistService.removeStock(investorId.toInt(), watchlistStockDto.stock))
    }
}
