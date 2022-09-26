package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Posting(
    @JsonProperty("id") val id: Int,
    @JsonProperty("userId") val userId: Int,
    @JsonProperty("images") val images: ArrayList<Image>,
    @JsonProperty("contents") val contents: String,
) {
    override fun toString(): String {
        return "Posting { id : $id, userId : $userId, images : ${images.map { it -> it.toString() }}, contents : $contents}"
    }
}

