package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Comment(
    @JsonProperty("id") val id: Int,
    @JsonProperty("userId") val userId: Int,
    @JsonProperty("postingId") val postingId: Int,
    @JsonProperty("contents") val contents: String,
) {
    override fun toString(): String {
        return "Comment { id : $id, userId : $userId, postingId : $postingId, contents : $contents }"
    }
}
