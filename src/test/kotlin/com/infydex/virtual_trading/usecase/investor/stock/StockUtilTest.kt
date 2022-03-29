package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.HoldingResponseDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionType
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
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

    @Test
    fun hasEnoughHoldingTest() {
        val holdings = listOf(
            StockEntity().copy(price = 100.0, type = StockTransactionType.BUY, quantity = 10, stockSymbol = "WIPRO"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.SELL, quantity = 10, stockSymbol = "WIPRO"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.BUY, quantity = 10, stockSymbol = "WIPRO"),
        )

        val stockTransactionDtoTrue = StockTransactionDto(quantity = 10, stockSymbol = "WIPRO", price = 100.0)
        val stockTransactionDtoFalse = StockTransactionDto(quantity = 20, stockSymbol = "WIPRO", price = 100.0)

        assertTrue(StockUtil.hasEnoughHolding(investorHolding = holdings, transactionDto = stockTransactionDtoTrue))
        assertFalse(StockUtil.hasEnoughHolding(investorHolding = holdings, transactionDto = stockTransactionDtoFalse))
    }

    @Test
    fun getHoldingsTest() {
        val transaction = listOf(
            StockEntity().copy(price = 100.0, type = StockTransactionType.BUY, quantity = 10, stockSymbol = "WIPRO"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.SELL, quantity = 10, stockSymbol = "WIPRO"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.BUY, quantity = 10, stockSymbol = "WIPRO"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.BUY, quantity = 10, stockSymbol = "TCS"),
            StockEntity().copy(price = 100.0, type = StockTransactionType.SELL, quantity = 10, stockSymbol = "TCS"),
        )

        val expected = listOf(
            HoldingResponseDto(
                stockSymbol = "WIPRO",
                cost = 1000.0,
                averagePrice = 100.0,
                quantity = 10
            )
        )

        assertEquals(expected, StockUtil.getHoldings(transaction))
    }
}
