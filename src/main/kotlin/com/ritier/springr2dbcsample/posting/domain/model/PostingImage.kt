package com.ritier.springr2dbcsample.posting.domain.model

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.ImageId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingImageId

data class PostingImage(
    val id: PostingImageId,
    val postingId: PostingId,
    val imageId: ImageId,
    val posting: Posting? = null,
    val image: Image? = null
) {
    fun isValidRelation(): Boolean = postingId.value > 0 && imageId.value > 0
}