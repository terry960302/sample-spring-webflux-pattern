package com.ritier.springr2dbcsample.entity.mapper

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.PostingImage
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class PostingImageMapper : BiFunction<Row, RowMetadata, PostingImage> {
    override fun apply(row: Row, metadata: RowMetadata): PostingImage {
        return PostingImage(
            id = row.get("posting_image_id").toString().toLong(),
            postingId = row.get("posting_id").toString().toLong(),
            imageId = row.get("image_id").toString().toLong(),
            image = Image(
                id = row.get("image_id").toString().toLong(),
                width = row.get("width").toString().toInt(),
                height = row.get("height").toString().toInt(),
                url = row.get("url").toString(),
                createdAt = row.get("created_at").toString(),
            ),
            posting = null,
        )
    }
}