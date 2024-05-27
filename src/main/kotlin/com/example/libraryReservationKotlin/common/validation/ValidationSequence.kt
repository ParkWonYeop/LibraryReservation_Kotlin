package com.example.libraryReservationKotlin.common.validation

import com.example.libraryReservationKotlin.common.validation.ValidationGroups.FutureGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotBlankGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.NotNullGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PatternGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.PositiveGroup
import com.example.libraryReservationKotlin.common.validation.ValidationGroups.SizeGroup
import jakarta.validation.GroupSequence

@GroupSequence(value = [NotBlankGroup::class, NotNullGroup::class, PatternGroup::class, SizeGroup::class, FutureGroup::class, PositiveGroup::class])
interface ValidationSequence
