package com.ritier.springr2dbcsample.posting.application.command

import com.ritier.springr2dbcsample.image.domain.model.Image

data class CreatePostingCommand(
    val userId: Long,
    val contents: String,
    val images: List<Image> = emptyList()
)
