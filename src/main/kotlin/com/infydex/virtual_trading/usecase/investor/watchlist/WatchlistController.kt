package com.infydex.virtual_trading.usecase.investor.watchlist

import com.infydex.virtual_trading.usecase.investor.watchlist.entity.WatchlistEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/watchlist")
class WatchlistController {
    @Autowired
    lateinit var watchlistService: WatchlistService

    @GetMapping("/")
    fun getWatchlist(request: HttpServletRequest): Optional<List<WatchlistEntity>> {
        val investorId = request.userPrincipal.name
        return watchlistService.getWatchlist(investorId.toInt())
    }
}
