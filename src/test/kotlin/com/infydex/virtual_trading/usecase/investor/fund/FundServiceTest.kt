package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.exception.InvalidInvestorIdException
import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.dto.FundResponseDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.infydex.virtual_trading.usecase.investor.fund.entity.TransactionType
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class FundServiceTest {
    @Mock
    lateinit var fundRepository: FundRepository

    @InjectMocks
    lateinit var fundService: FundService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should add funds in db`() {
        given(fundRepository.save(any(FundEntity::class.java)))
            .willReturn(FundEntity())

        val addFundDto = AddFundDto(amount = 200.0)
        fundService.createFundEntry(1, addFundDto, TransactionType.CREDIT)

        verify(fundRepository, times(1)).save(any(FundEntity::class.java))
    }

    @Test
    fun `should throw exception when provided invalid investor id`() {
        given(fundRepository.save(any(FundEntity::class.java)))
            .willThrow(InvalidInvestorIdException())

        val addFundDto = AddFundDto(amount = 200.0)

        assertThrows<InvalidInvestorIdException> { fundService.createFundEntry(1, addFundDto, TransactionType.CREDIT) }
    }

    @Test
    fun `should get available funds from db`() {
        val transactions = listOf(
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.CREDIT),
            FundEntity().copy(amount = 100.0, transactionType = TransactionType.DEBIT),
            FundEntity().copy(amount = 200.0, transactionType = TransactionType.CREDIT)
        )

        given(fundRepository.findAllByInvestorId(anyInt()))
            .willReturn(transactions)

        assertEquals(FundResponseDto(fund = 300.0), fundService.getAvailableFund(1))
    }
}
