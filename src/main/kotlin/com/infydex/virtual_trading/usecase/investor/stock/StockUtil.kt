package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto

object StockUtil {
    private fun addOrRemoveFund(currentBalance: Double, fund: FundEntity): Double {
        if (fund.transactionType == TransactionType.CREDIT) {
            return currentBalance + fund.amount
        }
        return currentBalance - fund.amount
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
}
