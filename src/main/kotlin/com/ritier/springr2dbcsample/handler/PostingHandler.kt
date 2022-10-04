package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.dto.PostingDto
import com.ritier.springr2dbcsample.dto.UserDto
import com.ritier.springr2dbcsample.dto.common.CommonError
import com.ritier.springr2dbcsample.dto.common.ErrorResponseDto
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.service.CommentService
import com.ritier.springr2dbcsample.service.PostingService
import kotlinx.coroutines.reactive.awaitSingle
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class PostingHandler(val postingService: PostingService, val commentService: CommentService) {
//    @Autowired
//    private lateinit var postingService: PostingService
//
//    @Autowired
//    private lateinit var commentService: CommentService

    suspend fun getPosting(request: ServerRequest): ServerResponse {
        val logger = LogManager.getLogger()
        return try {
            val postingId = request.pathVariable("id").toLong()
            val posting: PostingDto =
                postingService.findById(postingId) ?: throw Error("There's no posting data by ID($postingId)")
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(posting)
        } catch (e: Error) {
            logger.error("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun getAllPostings(request: ServerRequest): ServerResponse {
        val logger = LogManager.getLogger()
        return try {
            val postings = postingService.findAll()
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(postings)
        } catch (e: Error) {
            logger.error("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun getAllCommentsByPostingId(request: ServerRequest): ServerResponse {
        val logger = LogManager.getLogger()
        return try {
            val postingId = request.pathVariable("id").toLong()
            val comments = commentService.findCommentsByPostingId(postingId)
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(comments)
        } catch (e: Error) {
            logger.error("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }
}