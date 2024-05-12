package com.example.libraryReservationKotlin.common.validation.enumValidation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EnumValidator(var annotation: EnumValid) : ConstraintValidator<EnumValid, String> {
    override fun initialize(constraintAnnotation: EnumValid) {
        this.annotation = constraintAnnotation
    }

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        val enumValues = annotation.enumClass.java.enumConstants

        for (enumValue in enumValues) {
            if (value == enumValue.toString() ||
                (annotation.ignoreCase && value.equals(enumValue.toString(), ignoreCase = true))
            ) {
                return true
            }
        }

        return false
    }
}
