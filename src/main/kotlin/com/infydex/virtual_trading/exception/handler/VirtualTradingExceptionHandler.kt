package com.infydex.virtual_trading.exception.handler

import com.infydex.virtual_trading.exception.*
import com.infydex.virtual_trading.exception.dto.ErrorResponse
import com.infydex.virtual_trading.exception.message.ErrorMessage
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

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = ErrorMessage.INTERNAL_SERVER_ERROR
                )
            )
    }

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

    @ExceptionHandler(InvestorDoesNotExistsException::class, InvestorDoesNotExistsException::class)
    fun investorDoesNotExistsExceptionHandler(exception: InvestorDoesNotExistsException): ResponseEntity<ErrorResponse> {
        val mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(exception)
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    message = mostSpecificCause.message ?: ""
                )
            )
    }

    @ExceptionHandler(InsufficientFundException::class, InsufficientFundException::class)
    fun insufficientFundExceptionHandler(exception: InsufficientFundException): ResponseEntity<ErrorResponse> {
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
}
