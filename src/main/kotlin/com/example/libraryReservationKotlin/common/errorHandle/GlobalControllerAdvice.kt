package com.example.libraryReservationKotlin.common.errorHandle

import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {
    private val log = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)

    @ExceptionHandler(
        value = [
            MethodArgumentNotValidException::class,
            BindException::class,
        ],
    )
    fun handleMethodArgumentNotValidException(methodArgumentNotValidException: MethodArgumentNotValidException): ResponseEntity<String> {
        var errorMessage = methodArgumentNotValidException.message
        if (errorMessage.contains("Failed to convert value of type") || errorMessage.contains("JSON parse error")) {
            errorMessage = "타입이 잘못되었습니다."
        } else {
            errorMessage = methodArgumentNotValidException.fieldErrors[0].defaultMessage.toString()
        }
        log.info(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(value = [JwtException::class])
    fun handleJwtException(exception: JwtException): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.badRequest().body(exception.message)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(exception: Exception): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.internalServerError().body(exception.message)
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.badRequest().body(exception.message)
    }

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(exception: CustomException): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.status(exception.httpStatus).body(exception.message)
    }

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException): ResponseEntity<String> {
        var errorMessage: String? = exception.message
        if (errorMessage != null) {
            if (errorMessage.contains("Failed to convert value of type") || errorMessage.contains("JSON parse error")) {
                errorMessage = "타입이 잘못되었습니다."
            }
        }
        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<String> {
        var errorMessage: String? = exception.message
        if (errorMessage != null) {
            if (errorMessage.contains("Failed to convert value of type") || errorMessage.contains("JSON parse error")) {
                return ResponseEntity.badRequest().body("타입이 잘못되었습니다.")
            }
        }
        log.error(errorMessage, exception)
        return ResponseEntity.internalServerError().body(errorMessage)
    }
}
