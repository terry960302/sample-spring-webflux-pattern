package com.ritier.springr2dbcsample.presentation.handler

import com.ritier.springr2dbcsample.application.service.CommentService
import com.ritier.springr2dbcsample.application.service.PostingService
import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class PostingHandler(val postingService: PostingService, val commentService: CommentService) {

    private val logger = KotlinLogging.logger {}

    suspend fun getPostingById(request: ServerRequest): ServerResponse {
        val postingId = extractPostingId(request)
        val posting = postingService.findPostingById(postingId)

        logger.info { "게시글 조회 완료: postingId=$postingId" }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(posting)
    }

    suspend fun getPostings(request: ServerRequest): ServerResponse {
        val postings = postingService.findAllPostings()

        logger.info { "전체 게시글 조회 완료" }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyAndAwait(postings)
    }

    suspend fun getPostingComments(request: ServerRequest): ServerResponse {
        val postingId = extractPostingId(request)
        val comments = commentService.findByPostingId(postingId)

        logger.info { "게시글 댓글 조회 완료: postingId=$postingId" }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyAndAwait(comments)
    }

    private fun extractPostingId(request: ServerRequest): Long {
        return request.pathVariable("id").toLongOrNull()
            ?: throw AppException(ErrorCode.POSTING_ID_INVALID)
    }
}