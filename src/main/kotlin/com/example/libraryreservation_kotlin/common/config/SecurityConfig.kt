package com.example.libraryreservation_kotlin.common.config

import com.example.libraryreservation_kotlin.common.enum.PermissionEnum
import com.example.libraryreservation_kotlin.common.jwt.JwtFilter
import com.example.libraryreservation_kotlin.common.repository.TokenRepository
import com.example.libraryreservation_kotlin.common.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(var userRepository: UserRepository, var tokenRepository: TokenRepository){
    @Bean
    protected fun securityFilterChain(http: HttpSecurity): HttpSecurity? {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/login", "/auth/signup", "/auth/test").permitAll()
                it.requestMatchers(HttpMethod.PUT, "/auth/token").permitAll();
                it.requestMatchers(HttpMethod.GET, "/auth/token").authenticated();
                it.requestMatchers("/room/**", "/reservation/**").authenticated();
                it.requestMatchers("/admin/**").hasAnyRole(PermissionEnum.ADMIN.toString());
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterAfter(JwtFilter(userRepository, tokenRepository), UsernamePasswordAuthenticationFilter::class.java)
            .build()

        return http
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}