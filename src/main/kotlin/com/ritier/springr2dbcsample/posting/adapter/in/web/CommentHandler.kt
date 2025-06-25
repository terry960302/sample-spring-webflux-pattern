package com.ritier.springr2dbcsample.posting.adapter.`in`.web

import com.ritier.springr2dbcsample.posting.application.service.CommentService
import org.springframework.stereotype.Component

@Component
class CommentHandler(private val commentService: CommentService) {


}