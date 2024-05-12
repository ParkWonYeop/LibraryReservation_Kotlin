package com.example.libraryReservationKotlin.reservation.dto

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.FutureGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class ReservationDto(
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    var roomType: RoomEnum,
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    val seatNumber: Int,
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    @field:Future(message = "현재보다 과거입니다.", groups = [FutureGroup::class])
    val startTime: LocalDateTime,
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    val endTime: LocalDateTime,
)
