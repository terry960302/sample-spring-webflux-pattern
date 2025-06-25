package com.ritier.springr2dbcsample.image.domain.vo

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errors: List<String>) : ValidationResult()
}