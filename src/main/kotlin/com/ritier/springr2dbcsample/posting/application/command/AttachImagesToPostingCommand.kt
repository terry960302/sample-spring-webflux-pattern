package com.ritier.springr2dbcsample.posting.application.command

import com.ritier.springr2dbcsample.image.domain.model.Image

data class AttachImagesToPostingCommand(
    val postingId: Long,
    val editorUserId: Long,
    val images: List<Image>
)
