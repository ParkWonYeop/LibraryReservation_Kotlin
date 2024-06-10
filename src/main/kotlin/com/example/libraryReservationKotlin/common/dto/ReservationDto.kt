package com.example.libraryReservationKotlin.common.dto

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.FutureGroup
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Future
import java.time.LocalDateTime

data class ReservationDto @JsonCreator constructor(
    @JsonProperty("roomType")
    val roomType: RoomEnum,
    @JsonProperty("seatNumber")
    val seatNumber: Int,
    @field:Future(message = "현재보다 과거입니다.", groups = [FutureGroup::class])
    @JsonProperty("startTime")
    val startTime: LocalDateTime,
    @field:Future(message = "현재보다 과거입니다.", groups = [FutureGroup::class])
    @JsonProperty("endTime")
    val endTime: LocalDateTime,
)
