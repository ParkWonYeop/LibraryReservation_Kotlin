package com.example.libraryReservationKotlin.common.dto

import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotNullGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PositiveGroup
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ReservationDeleteDto(
    @field:NotNull(message = "값이 null 입니다.", groups = [NotNullGroup::class])
    @field:Positive(message = "값이 음수입니다.", groups = [PositiveGroup::class])
    val id: Long? = null,
) {
    fun getNotNullId() = id
        ?: throw CustomException(CommunalResponse.ID_IS_NULL)
}
