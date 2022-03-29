package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType

object FundUtils {
    private fun addOrRemoveFund(currentBalance: Double, fund: FundEntity): Double {
        if (fund.transactionType == TransactionType.CREDIT)
            return currentBalance + fund.amount
        return currentBalance - fund.amount
    }

    fun getAvailableFund(investorFunds: List<FundEntity>): Double {
        return investorFunds.fold(0.0) { acc, fundEntity ->
            addOrRemoveFund(acc, fundEntity)
        }
    }
}
