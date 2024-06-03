package com.example.libraryReservationKotlin.room.dto

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import jakarta.validation.constraints.NotNull

data class RoomTypeDto(
    @field:NotNull(message = "값이 null 입니다.", groups = [NotBlankGroup::class])
    val roomType: RoomEnum? = null,
) {
    fun getNotNullRoomType() = roomType
        ?: throw CustomException(CommunalResponse.ROOM_ENUM_IS_NULL)
}
