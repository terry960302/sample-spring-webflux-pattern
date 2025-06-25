package com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto


data class CreatePostingRequest(
    val userId : Long,
    val contents : String,
    val images : List<ImageDto>
)