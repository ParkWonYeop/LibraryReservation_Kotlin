package com.example.libraryReservationKotlin.auth

import com.example.libraryReservationKotlin.auth.dto.LoginDto
import com.example.libraryReservationKotlin.auth.dto.LoginResponseDto
import com.example.libraryReservationKotlin.auth.dto.RefreshDto
import com.example.libraryReservationKotlin.auth.dto.RefreshResponseDto
import com.example.libraryReservationKotlin.auth.dto.SignupDto
import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(
        @Validated(ValidationSequence::class) @RequestBody loginDto: LoginDto,
    ): LoginResponseDto = authService.login(loginDto)

    @PostMapping("/signup")
    fun signup(
        @Validated(ValidationSequence::class) @RequestBody signupDto: SignupDto,
    ) = authService.signup(signupDto)

    @PutMapping("/token")
    fun refreshToken(
        @Validated(ValidationSequence::class) @RequestBody refreshDto: RefreshDto,
    ): RefreshResponseDto = authService.refreshToken(refreshDto)
}
