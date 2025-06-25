package com.ritier.springr2dbcsample.image.domain.vo

sealed class StorageResult {
    data class Success(val url: String) : StorageResult()
    data class Failure(val error: String) : StorageResult()
}