package com.example.libraryReservationKotlin.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class QueryStringFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        filterChain.doFilter(RequestWrapper(request), response)
    }

    class RequestWrapper(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {
        override fun getParameterValues(
            parameter: String,
        ): Array<String>? = super.getParameterValues(parameter)
            ?: super.getParameterValues(toSnakeCase(parameter))

        override fun getParameter(
            parameter: String,
        ): String? = super.getParameter(parameter)
            ?: super.getParameter(toSnakeCase(parameter))

        private fun toSnakeCase(
            value: String,
        ): String = value
            .replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")
            .lowercase(Locale.getDefault())
    }
}
