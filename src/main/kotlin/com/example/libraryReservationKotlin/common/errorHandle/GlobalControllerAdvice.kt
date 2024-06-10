package com.example.libraryReservationKotlin.common.errorHandle

import com.example.libraryReservationKotlin.common.enum.JwtErrorEnum
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalControllerAdvice {
    private val log = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)

    @ExceptionHandler(
        value = [
            MethodArgumentNotValidException::class,
            BindException::class,
        ],
    )
    fun handleMethodArgumentNotValidException(
        methodArgumentNotValidException: MethodArgumentNotValidException,
    ): ResponseEntity<String> {
        val errorMessage = if (methodArgumentNotValidException.message.contains("Failed to convert value of type") || methodArgumentNotValidException.message.contains("JSON parse error")) {
            "타입이 잘못되었습니다."
        } else {
            methodArgumentNotValidException.fieldErrors[0].defaultMessage.toString()
        }

        log.info(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(
        value = [
            SignatureException::class,
            MalformedJwtException::class,
            ExpiredJwtException::class,
            IllegalArgumentException::class,
        ],
    )
    fun handleJwtException(
        exception: Exception,
    ): ResponseEntity<String> {
        val errorMessage = when (exception) {
            is SignatureException -> {
                JwtErrorEnum.WRONG_TYPE_TOKEN.msg
            }
            is MalformedJwtException -> {
                JwtErrorEnum.UNSUPPORTED_TOKEN.msg
            }
            is ExpiredJwtException -> {
                JwtErrorEnum.EXPIRED_TOKEN.msg
            }
            else -> {
                JwtErrorEnum.UNKNOWN_ERROR.msg
            }
        }

        log.error(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(
        exception: Exception,
    ): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.internalServerError().body(exception.message)
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDeniedException(
        exception: AccessDeniedException,
    ): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.badRequest().body(exception.message)
    }

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(
        exception: CustomException,
    ): ResponseEntity<String> {
        log.error(exception.message, exception)
        return ResponseEntity.status(exception.httpStatus).body(exception.message)
    }

    @ExceptionHandler(
        value = [
            MissingServletRequestParameterException::class,
            MethodArgumentTypeMismatchException::class,
            HttpMessageNotReadableException::class,
        ],
    )
    fun handleMissingServletRequestParameterException(
        exception: Exception,
    ): ResponseEntity<String> {
        var errorMessage: String? = exception.message

        errorMessage?.let {
            if (it.contains("Failed to convert value of type") || it.contains("JSON parse error")) {
                errorMessage = "타입이 잘못되었습니다."
            }
        }

        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun handleRuntimeException(
        exception: Exception,
    ): ResponseEntity<String> {
        val errorMessage: String? = exception.message

        log.error(exception.toString())
        return ResponseEntity.internalServerError().body(errorMessage)
    }
}
