package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FundUtilsTest {
    @Test
    fun getAvailableFundTest() {
        val transactions = listOf(
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.DEBIT),
            FundEntity().copy(amount = 200.0, transactionType = TransactionType.CREDIT)
        )
        assertEquals(300.0, FundUtils.getAvailableFund(transactions))
    }
}
