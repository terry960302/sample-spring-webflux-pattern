package com.ritier.springr2dbcsample.image.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.image.domain.model.Image
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class ImageReadConverter : Converter<Row, Image> {
    override fun convert(row: Row): Image {
        return Image.fromRow(row)
    }
}