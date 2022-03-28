package com.infydex.virtual_trading.exception.handler

import com.infydex.virtual_trading.exception.InvalidInvestorIdException
import com.infydex.virtual_trading.exception.InvalidLoginCredentialsException
import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.exception.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.core.NestedExceptionUtils
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class VirtualTradingExceptionHandler {
    val logger = LoggerFactory.getLogger(this.javaClass.name)!!

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(exception: MethodArgumentNotValidException): ResponseEntity<MutableMap<String, String?>> {
        val errors: MutableMap<String, String?> = HashMap()
        exception.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            errors[fieldName] = error.defaultMessage
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(PhoneNumberAlreadyRegisteredException::class, DuplicateKeyException::class)
    fun duplicateInvestorExceptionHandler(exception: PhoneNumberAlreadyRegisteredException): ResponseEntity<ErrorResponse> {
        val mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(exception)
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                ErrorResponse(
                    status = HttpStatus.CONFLICT.value(),
                    message = mostSpecificCause.message ?: ""
                )
            )
    }

    @ExceptionHandler(InvalidInvestorIdException::class, InvalidInvestorIdException::class)
    fun invalidInvestorIdExceptionHandler(exception: InvalidInvestorIdException): ResponseEntity<ErrorResponse> {
        val mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(exception)
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = mostSpecificCause.message ?: ""
                )
            )
    }

    @ExceptionHandler(InvalidLoginCredentialsException::class, InvalidLoginCredentialsException::class)
    fun invalidLoginCredentialsExceptionHandler(exception: InvalidLoginCredentialsException): ResponseEntity<ErrorResponse> {
        val mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(exception)
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ErrorResponse(
                    status = HttpStatus.UNAUTHORIZED.value(),
                    message = mostSpecificCause.message ?: ""
                )
            )
    }
}
