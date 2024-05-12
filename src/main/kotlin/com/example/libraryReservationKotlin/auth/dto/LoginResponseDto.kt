package com.example.libraryReservationKotlin.auth.dto

data class LoginResponseDto(
    var refreshToken: String,
    var accessToken: String,
)
