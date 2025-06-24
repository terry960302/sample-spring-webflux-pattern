package com.ritier.springr2dbcsample.domain.vo.image

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errors: List<String>) : ValidationResult()
}