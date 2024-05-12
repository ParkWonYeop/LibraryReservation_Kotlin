package com.example.libraryReservationKotlin.common.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

class LoggingInterceptor : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        requestLogging(request)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        log.info(
            "Response. Method: {}, URI: {}, Status: {}",
            request.method,
            request.requestURI,
            response.status.toString(),
        )
        MDC.clear()
    }

    private fun requestLogging(request: HttpServletRequest) {
        val headerNames = request.headerNames
        val headers: MutableMap<String, String> = HashMap()

        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement()
            headers[headerName] = request.getHeader(headerName)
        }

        MDC.put("IP", request.remoteAddr)
        MDC.put("Method", request.method)
        MDC.put("URI", request.requestURI)
        MDC.put("Headers", headers.toString())

        log.info("Request")
    }
}
