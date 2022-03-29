package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.exception.InsufficientFundException
import com.infydex.virtual_trading.exception.InsufficientHoldingException
import com.infydex.virtual_trading.usecase.investor.fund.FundRepository
import com.infydex.virtual_trading.usecase.investor.fund.FundService
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionType
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import com.infydex.virtual_trading.usecase.investor.stock.entity.TransactionStatus
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class StockServiceTest {
    @Mock
    lateinit var stockRepository: StockRepository

    @Mock
    lateinit var fundRepository: FundRepository

    @Mock
    lateinit var fundService: FundService

    @InjectMocks
    lateinit var stockService: StockService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        stockService = StockService(stockRepository, fundRepository, fundService)
    }

    @Test
    fun `should create buy transaction in db`() {
        given(stockRepository.save(any(StockEntity::class.java)))
            .willReturn(StockEntity())

        given(fundRepository.findAllByInvestorId(1)).willReturn(
            listOf(
                FundEntity().copy(
                    amount = 1000.0,
                    transactionType = TransactionType.CREDIT
                )
            )
        )

        val stockTransactionDto = StockTransactionDto(
            stockSymbol = "WIPRO",
            price = 100.0,
            quantity = 10
        )

        stockService.buy(1, stockTransactionDto)

        verify(stockRepository).save(
            StockEntity(
                investorId = 1,
                stockSymbol = stockTransactionDto.stockSymbol,
                quantity = stockTransactionDto.quantity,
                type = StockTransactionType.BUY,
                price = stockTransactionDto.price,
                status = TransactionStatus.COMPLETED
            )
        )
    }

    @Test
    fun `should throw Insufficient fund exception funds are lower than stock cost`() {
        given(stockRepository.save(any(StockEntity::class.java)))
            .willReturn(StockEntity())

        given(fundRepository.findAllByInvestorId(1)).willReturn(
            listOf(
                FundEntity().copy(
                    amount = 1000.0,
                    transactionType = TransactionType.CREDIT
                )
            )
        )

        val stockTransactionDto = StockTransactionDto(
            stockSymbol = "WIPRO",
            price = 1000.0,
            quantity = 10
        )

        assertThrows<InsufficientFundException> { stockService.buy(1, stockTransactionDto) }
    }

    @Test
    fun `should create sell transaction in db`() {
        val holdings = listOf(
            StockEntity().copy(
                stockSymbol = "WIPRO",
                quantity = 10,
                status = TransactionStatus.COMPLETED
            )
        )

        given(stockRepository.save(any(StockEntity::class.java)))
            .willReturn(StockEntity())

        given(stockRepository.findAllByInvestorIdAndStockSymbol(anyInt(), anyString()))
            .willReturn(holdings)

        val stockTransactionDto = StockTransactionDto(
            stockSymbol = "WIPRO",
            price = 100.0,
            quantity = 10
        )

        stockService.sell(1, stockTransactionDto)

        verify(stockRepository).save(
            StockEntity(
                investorId = 1,
                stockSymbol = stockTransactionDto.stockSymbol,
                quantity = stockTransactionDto.quantity,
                type = StockTransactionType.SELL,
                price = stockTransactionDto.price,
                status = TransactionStatus.COMPLETED
            )
        )
    }

    @Test
    fun `should two insufficient holding exception when does not have enough holding`() {
        val holdings = listOf(
            StockEntity().copy(
                stockSymbol = "WIPRO",
                quantity = 10,
                status = TransactionStatus.COMPLETED
            )
        )

        given(stockRepository.save(any(StockEntity::class.java)))
            .willReturn(StockEntity())

        given(stockRepository.findAllByInvestorIdAndStockSymbol(anyInt(), anyString()))
            .willReturn(holdings)

        val stockTransactionDto = StockTransactionDto(
            stockSymbol = "WIPRO",
            price = 100.0,
            quantity = 100
        )

        assertThrows<InsufficientHoldingException> { stockService.sell(1, stockTransactionDto) }
    }
}
