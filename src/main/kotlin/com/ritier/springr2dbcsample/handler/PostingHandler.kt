package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.dto.UserDto
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.service.PostingService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class PostingHandler {
    @Autowired
    private lateinit var postingService: PostingService

    suspend fun getAllPostings(request: ServerRequest): ServerResponse {
        val logger = LogManager.getLogger()
        return try {
            val postings = postingService.findAll()
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(postings)
        } catch (e: Error) {
            logger.error("Error : ${e.message}")
            ServerResponse.notFound().buildAndAwait()
        }
    }
}