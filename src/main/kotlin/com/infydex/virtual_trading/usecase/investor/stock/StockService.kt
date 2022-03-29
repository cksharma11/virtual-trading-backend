package com.infydex.virtual_trading.usecase.investor.stock

import com.infydex.virtual_trading.exception.InsufficientFundException
import com.infydex.virtual_trading.usecase.investor.fund.FundRepository
import com.infydex.virtual_trading.usecase.investor.fund.FundService
import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionDto
import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionType
import com.infydex.virtual_trading.usecase.investor.stock.entity.StockEntity
import com.infydex.virtual_trading.usecase.investor.stock.entity.TransactionStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository,
    private val fundRepository: FundRepository,
    private val fundService: FundService
) {
    @Transactional
    fun buy(investorId: Int, stockTransactionDto: StockTransactionDto): StockEntity {
        val investorFunds = fundRepository.findAllByInvestorId(investorId)
        val currentBalance = StockUtil.getCurrentBalance(investorFunds)

        val stocksCost = StockUtil.getStockCost(stockTransactionDto)

        if (!StockUtil.canBuyStocks(currentBalance, stocksCost)) {
            throw InsufficientFundException()
        }

        fundService.createFundEntry(investorId, AddFundDto(amount = stocksCost), TransactionType.DEBIT)

        return stockRepository.save(
            StockEntity().copy(
                investorId = investorId,
                stockSymbol = stockTransactionDto.stockSymbol,
                quantity = stockTransactionDto.quantity,
                type = StockTransactionType.BUY,
                price = stockTransactionDto.price,
                status = TransactionStatus.COMPLETED
            )
        )
    }
}