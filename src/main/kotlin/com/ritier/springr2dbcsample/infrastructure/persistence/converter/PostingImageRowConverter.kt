package com.ritier.springr2dbcsample.infrastructure.persistence.converter

import com.ritier.springr2dbcsample.domain.model.Image
import org.springframework.stereotype.Component

@Component
object PostingImageRowConverter {

    private fun convertFromRow(row: Map<String, *>): Image? {
        val imageId = row["posting_img_id"]?.toString()?.toLongOrNull() ?: return null
        val url = row["posting_img_url"]?.toString() ?: return null
        val width = row["posting_img_width"]?.toString()?.toIntOrNull() ?: return null
        val height = row["posting_img_height"]?.toString()?.toIntOrNull() ?: return null
        val createdAtStr = row["posting_img_created_at"]?.toString() ?: return null

        return Image.fromRowWithPrefix("posting_img_", row)
    }

    // 여러 row에서 이미지 리스트 추출
    fun convertFromRows(rowList: List<Map<String, *>>): List<Image> {
        return rowList
            .mapNotNull { row -> convertFromRow(row) }
            .distinctBy { it.id } // 중복 제거
    }
}