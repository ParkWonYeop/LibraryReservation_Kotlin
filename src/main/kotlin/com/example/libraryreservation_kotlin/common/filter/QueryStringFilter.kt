package com.example.libraryreservation_kotlin.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class QueryStringFilter: OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        filterChain.doFilter(RequestWrapper(request), response)
    }

    class RequestWrapper(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {
        override fun getParameterValues(parameter: String): Array<String>? {
            val values = super.getParameterValues(parameter) ?: return super.getParameterValues(toSnakeCase(parameter))

            return values
        }

        override fun getParameter(parameter: String): String {
            val value = super.getParameter(parameter) ?: return super.getParameter(toSnakeCase(parameter))

            return value;
        }

        private fun toSnakeCase(value: String): String {
            val regex = "([a-z])([A-Z]+)"
            val replacement = "$1_$2"
            return value.replace(regex.toRegex(), replacement).lowercase(Locale.getDefault())
        }
    }
}