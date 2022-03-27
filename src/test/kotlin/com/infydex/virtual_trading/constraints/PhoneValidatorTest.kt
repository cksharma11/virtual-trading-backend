package com.infydex.virtual_trading.constraints

import com.infydex.virtual_trading.constraints.phone.PhoneValidator
import org.junit.After
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.validation.ConstraintValidatorContext

@RunWith(MockitoJUnitRunner::class)
internal class PhoneValidatorTest {
    @InjectMocks
    lateinit var phoneValidator: PhoneValidator

    @Mock
    lateinit var context: ConstraintValidatorContext

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should return true for valid phone number`() {
        assertTrue(phoneValidator.isValid("9876543210", context))
        assertTrue(phoneValidator.isValid("1234567890", context))
    }

    @Test
    fun `should return false for invalid phone number`() {
        assertFalse(phoneValidator.isValid("12345", context))
        assertFalse(phoneValidator.isValid("a123456789", context))
        assertFalse(phoneValidator.isValid("", context))
        assertFalse(phoneValidator.isValid(null, context))
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(this)
    }
}
