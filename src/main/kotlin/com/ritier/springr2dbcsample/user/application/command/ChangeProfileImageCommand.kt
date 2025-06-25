package com.ritier.springr2dbcsample.user.application.command

import com.ritier.springr2dbcsample.image.domain.model.Image

data class ChangeProfileImageCommand(
    val userId: Long,
    val newImage: Image
)