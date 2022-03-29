package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.exception.InvalidInvestorIdException
import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.dto.FundResponseDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import org.springframework.stereotype.Service

@Service
class FundService(
    private val fundRepository: FundRepository,
) {
    fun createFundEntry(investorId: Int, addFundDto: AddFundDto, transactionType: TransactionType): FundEntity {
        try {
            return fundRepository.save(
                FundEntity().copy(
                    investorId = investorId,
                    amount = addFundDto.amount,
                    transactionType = transactionType
                )
            )
        } catch (ex: Exception) {
            throw InvalidInvestorIdException()
        }
    }

    fun getAvailableFund(investorId: Int): FundResponseDto {
        val transactions = fundRepository.findAllByInvestorId(investorId)
        return FundResponseDto(fund = FundUtils.getAvailableFund(transactions))
    }
}
