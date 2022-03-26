package com.infydex.virtual_trading.exception.handler

import com.infydex.virtual_trading.exception.BadRequestException
import com.infydex.virtual_trading.exception.PhoneNumberAlreadyRegisteredException
import com.infydex.virtual_trading.exception.UnauthorizedException
import com.infydex.virtual_trading.exception.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.core.NestedExceptionUtils
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestClientResponseException

@ControllerAdvice
class VirtualTradingExceptionHandler {
    val logger = LoggerFactory.getLogger(this.javaClass.name)!!

    @ExceptionHandler(RestClientResponseException::class)
    fun restClientResponseExceptionHandler(exception: RestClientResponseException): ResponseEntity<Any> {
        logger.error("Rest Client exception: " + exception.message)
        return ResponseEntity.status(exception.rawStatusCode).body(exception.responseBodyAsString)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestExceptionHandler(exception: BadRequestException): ResponseEntity<String?> {
        val mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(exception)
        logger.error(exception.message, exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mostSpecificCause.message ?: "")
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

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorized(exception: UnauthorizedException): ResponseEntity<ErrorResponse> {
        logger.warn(exception.message, exception)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(HttpStatus.UNAUTHORIZED.value()))
    }
}
