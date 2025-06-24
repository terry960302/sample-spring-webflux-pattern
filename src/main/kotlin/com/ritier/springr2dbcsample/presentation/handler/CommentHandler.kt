package com.ritier.springr2dbcsample.presentation.handler

import com.ritier.springr2dbcsample.application.service.CommentService
import org.springframework.stereotype.Component

@Component
class CommentHandler(private val commentService: CommentService) {


}