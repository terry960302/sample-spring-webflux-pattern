package com.ritier.springr2dbcsample.entity.converter

import com.ritier.springr2dbcsample.entity.Image
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class ImageReadConverter : Converter<Row, Image> {
    override fun convert(row: Row): Image {
        return Image(
            id = row.get("image_id").toString().toLong(),
            url = row.get("url").toString(),
            width = row.get("width").toString().toInt(),
            height = row.get("height").toString().toInt(),
            createdAt = com.ritier.springr2dbcsample.util.ConverterUtil.convertStrToLocalDateTime(row.get("created_at").toString()),
        )
    }
}