package com.example.libraryReservationKotlin.common.dto

import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PositiveGroup
import jakarta.validation.constraints.Positive

data class ReservationDeleteDto(
    @field:Positive(message = "값이 음수입니다.", groups = [PositiveGroup::class])
    val id: Long,
)
