package com.infydex.virtual_trading.usecase.investor.fund

import com.infydex.virtual_trading.usecase.investor.fund.dto.AddFundDto
import com.infydex.virtual_trading.usecase.investor.fund.entity.FundEntity
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
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
        fundService.addFund(1, addFundDto)

        verify(fundRepository, times(1)).save(any(FundEntity::class.java))
    }
}
