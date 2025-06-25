package com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto

data class CreateCommentRequest(
    val postingId : Long,
    val authorUserId : Long,
    val contents : String,
) {
}