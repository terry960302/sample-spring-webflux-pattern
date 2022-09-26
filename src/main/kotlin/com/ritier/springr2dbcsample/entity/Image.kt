package com.ritier.springr2dbcsample.entity

data class Image(val id: Int, val url: String) {
    override fun toString(): String {
        return "Image { id : $id, url : $url}"
    }
}
