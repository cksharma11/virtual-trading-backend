package com.infydex.virtual_trading.usecase.investor.onboarding

import com.infydex.virtual_trading.usecase.investor.onboarding.dto.InvestorSignupDto
import com.infydex.virtual_trading.usecase.investor.onboarding.dto.PinDto
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.InvestorEntity
import com.infydex.virtual_trading.usecase.investor.onboarding.entity.PinEntity
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

    @Mock
    lateinit var pinRepository: PinRepository

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

    @Test
    fun `should create investor pin in database in provided valid investor id`() {
        given(pinRepository.save(any(PinEntity::class.java))).willReturn(PinEntity())

        val pinDto = PinDto(pin = "1234", investorId = 1)
        investorService.createPin(pinDto)

        verify(pinRepository).save(any(PinEntity::class.java))
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(investorRepository)
    }
}
