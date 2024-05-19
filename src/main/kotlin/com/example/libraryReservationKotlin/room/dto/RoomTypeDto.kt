package com.example.libraryReservationKotlin.room.dto

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import jakarta.validation.constraints.NotNull

data class RoomTypeDto(
    @field:NotNull(message = "값이 없습니다.", groups = [NotBlankGroup::class])
    val roomType: RoomEnum,
)
