package com.example.libraryreservation_kotlin.auth.dto

data class LoginResponseDto(
    var refreshToken: String,
    var accessToken: String
)
