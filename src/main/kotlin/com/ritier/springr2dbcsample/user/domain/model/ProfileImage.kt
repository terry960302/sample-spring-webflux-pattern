package com.ritier.springr2dbcsample.user.domain.model

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.ImageId
import com.ritier.springr2dbcsample.user.domain.vo.ProfileImageId
import com.ritier.springr2dbcsample.user.domain.vo.UserId

data class ProfileImage(
    val id: ProfileImageId,
    val userId: UserId,
    val imageId: ImageId,
    val user: User? = null,
    val image: Image? = null,
) {


}