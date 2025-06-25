package com.ritier.springr2dbcsample.user.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.image.domain.model.Image
import org.springframework.stereotype.Component

@Component
object ProfileImageRowConverter {

    fun convertFromRow(row: Map<String, *>): Image? {
//        val imageId = row["profile_img_id"]?.toString()?.toLongOrNull() ?: return null
//        val url = row["profile_img_url"]?.toString() ?: return null
//        val width = row["profile_img_width"]?.toString()?.toIntOrNull() ?: return null
//        val height = row["profile_img_height"]?.toString()?.toIntOrNull() ?: return null
//        val createdAtStr = row["profile_img_created_at"]?.toString() ?: return null

        return Image.fromRowWithPrefix("profile_img_", row)
    }
}