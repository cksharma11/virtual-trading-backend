package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.HoldingResponseDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionType
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import com.infydex.virtual_trading.usecase.investor.stock.entity.TransactionStatus

object StockUtil {
    private fun addOrRemoveFund(currentBalance: Double, fund: FundEntity): Double {
        if (fund.transactionType == TransactionType.CREDIT)
            return currentBalance + fund.amount
        return currentBalance - fund.amount
    }

    private fun addOrRemoveStock(
        transaction: StockTransactionDto,
        count: Int,
        holding: StockEntity,
    ): Int {
        if (holding.stockSymbol == transaction.stockSymbol && holding.status == TransactionStatus.COMPLETED) {
            if (holding.type == StockTransactionType.BUY)
                return count + holding.quantity
            return count - holding.quantity
        }
        return count
    }

    private fun createHoldingDto(holdings: List<StockEntity>): HoldingResponseDto {
        var count = 0
        var cost = 0.0

        for (holding in holdings) {
            if (holding.type == StockTransactionType.SELL) {
                count -= holding.quantity
                cost -= holding.price * (holding.quantity)
            } else {
                count += holding.quantity
                cost += holding.price * (holding.quantity)
            }
        }

        return HoldingResponseDto(
            stockSymbol = holdings[0].stockSymbol,
            quantity = count,
            averagePrice = cost / count,
            cost = cost
        )
    }

    fun getCurrentBalance(investorFunds: List<FundEntity>): Double {
        return investorFunds.fold(0.0) { acc, fundEntity ->
            addOrRemoveFund(acc, fundEntity)
        }
    }

    fun getStockCost(stockTransactionDto: StockTransactionDto): Double {
        return stockTransactionDto.price * stockTransactionDto.quantity
    }

    fun canBuyStocks(currentBalance: Double, stockCosts: Double): Boolean {
        return currentBalance >= stockCosts
    }

    fun hasEnoughHolding(investorHolding: List<StockEntity>, transactionDto: StockTransactionDto): Boolean {
        var currentStockHolding = 0
        for (holding in investorHolding) {
            currentStockHolding = addOrRemoveStock(transactionDto, currentStockHolding, holding)
        }

        return currentStockHolding >= transactionDto.quantity
    }

    fun getHoldings(transactions: List<StockEntity>): List<HoldingResponseDto> {
        val groupedHoldings = transactions.groupBy { it.stockSymbol }
        val holdings = groupedHoldings.map { createHoldingDto(it.value) }
        return holdings.filter { it.quantity != 0 }
    }
}
