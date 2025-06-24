package com.ritier.springr2dbcsample.domain.vo.image

sealed class StorageResult {
    data class Success(val url: String) : StorageResult()
    data class Failure(val error: String) : StorageResult()
}