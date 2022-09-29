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
import org.apache.logging.log4j.LogManager

@Component
class ImageHandler {

    @Autowired
    private lateinit var imageService: ImageService

    suspend fun uploadImages(serverRequest: ServerRequest): ServerResponse {
        val log = LogManager.getLogger()
        return try {
            val files: List<FilePart> =
                serverRequest.multipartData().map { it["files"] as List<FilePart> }.awaitSingle()

            val deferredUrls = coroutineScope {
                files.map { file ->
                    async(Dispatchers.IO) {
                        try{
                            val url: String = imageService.uploadImage(file)
                            url
                        }catch(e : Error){
                            "error : ${e.message}"
                        }
                    }
                }
            }
            val urls: List<String> = deferredUrls.awaitAll()
            log.info("urls : $urls")

            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(urls).awaitSingle()
        } catch (e: Error) {
            log.error("Error : ${e.message}")
            ServerResponse.notFound().buildAndAwait()
        }
    }

}