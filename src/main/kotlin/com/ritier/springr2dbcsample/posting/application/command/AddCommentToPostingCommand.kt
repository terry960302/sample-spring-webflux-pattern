package com.ritier.springr2dbcsample.posting.application.command

data class AddCommentToPostingCommand(
    val postingId: Long,
    val authorUserId: Long,
    val contents: String
)