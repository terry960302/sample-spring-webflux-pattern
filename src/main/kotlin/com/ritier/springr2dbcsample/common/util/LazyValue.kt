package com.ritier.springr2dbcsample.common.util

// 엔티티 -> 도메인 모델 변환시 연관관계에 대한 지연 매핑.
class LazyValue<T> private constructor(
    private val supplier: suspend () -> T
) {
    @Volatile
    private var cached: T? = null
    private var initialized = false
    private val lock = kotlinx.coroutines.sync.Mutex()

    suspend fun get(): T {
        if (initialized) return cached!!

        lock.lock()
        try {
            if (!initialized) {
                cached = supplier()
                initialized = true
            }
        } finally {
            lock.unlock()
        }

        return cached!!
    }

    companion object {
        fun <T> lazy(supplier: suspend () -> T): LazyValue<T> =
            LazyValue(supplier)

        fun <T> empty(): LazyValue<T> =
            LazyValue { throw IllegalStateException("LazyValue 가 초기화되지 않았습니다.") }
    }
}
