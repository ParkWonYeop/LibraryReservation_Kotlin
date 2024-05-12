package com.example.libraryReservationKotlin.reservation.dto

import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PositiveGroup
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ReservationDeleteDto(
    @field:NotNull(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    @field:Positive(message = "값이 음수입니다.", groups = [PositiveGroup::class])
    val id: Long,
)
