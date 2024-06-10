package com.example.libraryReservationKotlin.auth.dto

import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PatternGroup
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class RefreshDto @JsonCreator constructor(
    @field:Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.", groups = [PatternGroup::class])
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    @JsonProperty("phoneNumber")
    val phoneNumber: String,
    @field:NotBlank(message = "빈 문자열 입니다.", groups = [NotBlankGroup::class])
    @JsonProperty("refreshToken")
    val refreshToken: String,
)
