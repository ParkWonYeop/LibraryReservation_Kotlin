package com.example.libraryReservationKotlin.common.config

import com.example.libraryReservationKotlin.common.enum.PermissionEnum
import com.example.libraryReservationKotlin.common.filter.JwtFilter
import com.example.libraryReservationKotlin.common.filter.QueryStringFilter
import com.example.libraryReservationKotlin.common.repository.TokenRepository
import com.example.libraryReservationKotlin.common.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    @Value("\${secret_key}")
    private val secretKey: String,
) {
    @Bean
    protected fun securityFilterChain(
        http: HttpSecurity,
    ): DefaultSecurityFilterChain? = http
        .httpBasic { it.disable() }
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers("/auth/login", "/auth/signup", "/auth/test").permitAll()
            it.requestMatchers(HttpMethod.PUT, "/auth/token").permitAll()
            it.requestMatchers(HttpMethod.GET, "/auth/token").authenticated()
            it.requestMatchers("/room/**", "/reservation/**").authenticated()
            it.requestMatchers("/admin/**").hasAnyRole(PermissionEnum.ADMIN.toString())
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .addFilterBefore(JwtFilter(userRepository, tokenRepository, secretKey), UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(QueryStringFilter(), JwtFilter::class.java)
        .build()

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}
