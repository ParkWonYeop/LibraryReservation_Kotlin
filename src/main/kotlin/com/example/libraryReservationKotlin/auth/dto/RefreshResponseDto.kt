package com.example.libraryReservationKotlin.auth.dto

data class RefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
