package com.example.libraryReservationKotlin.common.utils

import org.springframework.security.core.context.SecurityContextHolder

class SecurityUtil {
    fun getCurrentMemberId(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || authentication.name == null) {
            throw RuntimeException("No authentication information.")
        }
        return authentication.name
    }
}
