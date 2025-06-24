package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.domain.vo.image.MultiImageUploadResults
import com.ritier.springr2dbcsample.domain.vo.image.SingleImageUploadResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.springframework.stereotype.Component

@Component
class ImageUploadExecutor {
    companion object {
        private const val MAX_CONCURRENT_UPLOADS = 5
    }

    suspend fun <T, R> executeParallel(
        items: List<T>,
        transform: suspend (T) -> R
    ): MultiImageUploadResults {
        val semaphore = Semaphore(MAX_CONCURRENT_UPLOADS)

        val results = coroutineScope {
            items.map { item ->
                async(Dispatchers.IO) {
                    semaphore.withPermit {
                        transform(item)
                    }
                }
            }
        }.awaitAll()

        return MultiImageUploadResults(results as List<SingleImageUploadResult>)
    }
}