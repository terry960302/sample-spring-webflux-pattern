package com.ritier.springr2dbcsample.domain.model

import com.ritier.springr2dbcsample.domain.vo.image.ImageId
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.posting.PostingImageId

data class PostingImage(
    val id: PostingImageId,
    val postingId: PostingId,
    val imageId: ImageId,
    val posting: Posting? = null,
    val image: Image? = null
) {
    fun isValidRelation(): Boolean = postingId.value > 0 && imageId.value > 0
}