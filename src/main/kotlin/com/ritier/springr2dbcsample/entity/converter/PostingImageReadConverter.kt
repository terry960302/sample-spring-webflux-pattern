package com.ritier.springr2dbcsample.entity.converter

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.PostingImage
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class PostingImageReadConverter : Converter<Row, PostingImage> {
    override fun convert(row: Row): PostingImage {
        return PostingImage(
            id = row.get("posting_image_id").toString().toLong(),
            postingId = row.get("posting_id").toString().toLong(),
            imageId = row.get("image_id").toString().toLong(),
            image = Image(
                id = row.get("image_id").toString().toLong(),
                width = row.get("width").toString().toInt(),
                height = row.get("height").toString().toInt(),
                url = row.get("url").toString(),
                createdAt = com.ritier.springr2dbcsample.util.ConverterUtil.convertStrToLocalDateTime(row.get("created_at").toString()),
            ),
            posting = null,
        )
    }
}