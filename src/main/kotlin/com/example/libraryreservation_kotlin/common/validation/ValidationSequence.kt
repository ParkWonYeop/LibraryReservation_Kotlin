package com.example.libraryreservation_kotlin.common.validation

import jakarta.validation.GroupSequence
import com.example.libraryreservation_kotlin.common.validation.ValidationGroups.*


@GroupSequence(value = [NotBlankGroup::class, PatternGroup::class, SizeGroup::class, FutureGroup::class, PositiveGroup::class])
interface ValidationSequence {
}