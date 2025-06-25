package com.ritier.springr2dbcsample.posting.application.command

data class UpdatePostingContentCommand(
    val postingId: Long,
    val editorUserId: Long,
    val newContent: String
)
