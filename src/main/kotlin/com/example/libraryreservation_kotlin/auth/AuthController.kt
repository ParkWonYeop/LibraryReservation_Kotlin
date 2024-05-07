package com.example.libraryreservation_kotlin.auth

import com.example.libraryreservation_kotlin.auth.dto.LoginDto
import com.example.libraryreservation_kotlin.auth.dto.LoginResponseDto
import com.example.libraryreservation_kotlin.auth.dto.SignupDto
import com.example.libraryreservation_kotlin.common.entity.TokenEntity
import com.example.libraryreservation_kotlin.common.validation.ValidationSequence
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@Validated(ValidationSequence::class) @RequestBody loginDto: LoginDto): LoginResponseDto {
        return authService.login(loginDto)
    }

    @PostMapping("/signup")
    fun signup(@Validated(ValidationSequence::class) @RequestBody signupDto: SignupDto) {
        authService.signup(signupDto)
    }
}