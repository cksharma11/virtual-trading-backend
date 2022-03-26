package com.infydex.virtual_trading.usecase.investor

import com.infydex.virtual_trading.usecase.investor.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.entity.InvestorEntity
import org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class InvestorServiceTest {
    @Mock
    lateinit var investorRepository: InvestorRepository

    @InjectMocks
    lateinit var investorService: InvestorService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create investor in database if not exists with phone number`() {
        given(investorRepository.save(any(InvestorEntity::class.java))).willReturn(InvestorEntity())

        val investor = InvestorSignupDto(phone = "1234567890")
        investorService.signup(investor)

        verify(investorRepository).save(any(InvestorEntity::class.java))
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(investorRepository)
    }
}
