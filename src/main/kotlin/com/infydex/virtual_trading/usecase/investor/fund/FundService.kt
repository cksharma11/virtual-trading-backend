package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import org.springframework.stereotype.Service

@Service
class FundService(
    private val fundRepository: FundRepository,
) {
    fun addFund(investorId: Int, addFundDto: AddFundDto): FundEntity {
        return fundRepository.save(
            FundEntity().copy(
                investorId = investorId,
                amount = addFundDto.amount,
                transactionType = TransactionType.CREDIT
            )
        )
    }
}
