package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StockUtilTest {

    @Test
    fun getCurrentBalanceTest() {
        val transactions = listOf(
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.DEBIT),
            FundEntity().copy(amount = 200.0, transactionType = TransactionType.CREDIT)
        )
        assertEquals(300.0, StockUtil.getCurrentBalance(transactions))
    }

    @Test
    fun getStockCostTest() {
        val stockTransactionDto = StockTransactionDto(
            stockSymbol = "WIPRO",
            price = 100.0,
            quantity = 10
        )
        assertEquals(StockUtil.getStockCost(stockTransactionDto = stockTransactionDto), 1000.0)
    }

    @Test
    fun canBuyStocksTest() {
        assertTrue(StockUtil.canBuyStocks(100.0, 90.0))
        assertFalse(StockUtil.canBuyStocks(100.0, 200.0))
    }
}
