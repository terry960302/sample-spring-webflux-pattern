package com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.posting.domain.model.PostingImage
import com.ritier.springr2dbcsample.image.domain.vo.ImageId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingImageId
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class PostingImageReadConverter : Converter<Row, PostingImage> {
    override fun convert(row: Row): PostingImage {
        return PostingImage(
            id = PostingImageId(row.get("posting_image_id").toString().toLong()),
            postingId = PostingId(row.get("posting_id").toString().toLong()),
            imageId = ImageId(row.get("image_id").toString().toLong()),
            image = Image.fromRow(row),
            posting = null,
        )
    }
}