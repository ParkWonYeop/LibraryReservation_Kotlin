package com.example.libraryReservationKotlin.common.dto

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.FutureGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotNullGroup
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationDto(
    @field:NotNull(message = "값이 null 입니다.", groups = [NotNullGroup::class])
    val roomType: RoomEnum? = null,
    @field:NotNull(message = "값이 null 입니다.", groups = [NotNullGroup::class])
    val seatNumber: Int? = null,
    @field:NotNull(message = "값이 null 입니다.", groups = [NotNullGroup::class])
    @field:Future(message = "현재보다 과거입니다.", groups = [FutureGroup::class])
    val startTime: LocalDateTime? = null,
    @field:NotNull(message = "값이 null 입니다.", groups = [NotNullGroup::class])
    @field:Future(message = "현재보다 과거입니다.", groups = [FutureGroup::class])
    val endTime: LocalDateTime? = null,
)
