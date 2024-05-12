package com.example.libraryReservationKotlin.common.filter

import com.example.libraryReservationKotlin.common.entity.TokenEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import com.example.libraryReservationKotlin.common.jwt.JwtUtil
import com.example.libraryReservationKotlin.common.repository.TokenRepository
import com.example.libraryReservationKotlin.common.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(private val userRepository: UserRepository, private val tokenRepository: TokenRepository, private val secretKey: String) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(JwtFilter::class.java)
    private val jwtUtil = JwtUtil()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if (!request.requestURI.contains("/auth")) {
            val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                log.error("authorization 이 없습니다.")
                filterChain.doFilter(request, response)
                return
            }

            val token: String = authorization.split(" ")[1]

            if (jwtUtil.isExpired(token, secretKey)) {
                log.error("Token이 만료되었습니다.")
                filterChain.doFilter(request, response)
                return
            }

            val phoneNumber: String = jwtUtil.getSubject(token, secretKey)

            if (phoneNumber == "") {
                log.error("토큰에 Subject가 없습니다.")
                filterChain.doFilter(request, response)
                return
            }

            val user: UserEntity? = userRepository.findByPhoneNumber(phoneNumber)

            if (user == null) {
                log.error("유저를 찾을 수 없습니다.")
                filterChain.doFilter(request, response)
                return
            }

            val checkToken: TokenEntity? = tokenRepository.findByUser(user)

            if (checkToken != null && checkToken.accessToken != token) {
                log.error("토큰이 유효하지 않습니다.")
                filterChain.doFilter(request, response)
                return
            }

            MDC.put("phoneNumber", token)

            val authenticationToken = UsernamePasswordAuthenticationToken(phoneNumber, null, listOf(SimpleGrantedAuthority("ROLE_" + user.permission.toString())))
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
        MDC.clear()
    }
}
