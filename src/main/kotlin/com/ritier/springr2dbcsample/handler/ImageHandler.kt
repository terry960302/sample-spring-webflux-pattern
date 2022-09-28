package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.service.ImageService
import kotlinx.coroutines.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class ImageHandler {

    @Autowired
    private lateinit var imageService: ImageService

    suspend fun uploadImages(serverRequest: ServerRequest): ServerResponse {
        return try {
            val files: List<FilePart> =
                serverRequest.multipartData().map { it -> it["files"] as List<FilePart> }.awaitSingle()

            val deferredUrls = coroutineScope {
                files.map { file ->
                    async(Dispatchers.IO) {
                        val url: String = imageService.uploadImage(file)
                        url
                    }
                }
            }
            val urls : List<String> = deferredUrls.awaitAll()

            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(urls).awaitSingle()
        } catch (e: Error) {
            println("Error : ${e.message}")
            ServerResponse.notFound().buildAndAwait()
        }
    }

}