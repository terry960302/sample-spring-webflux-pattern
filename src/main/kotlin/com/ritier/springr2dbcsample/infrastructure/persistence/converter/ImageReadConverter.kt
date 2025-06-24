package com.ritier.springr2dbcsample.infrastructure.persistence.converter

import com.ritier.springr2dbcsample.common.util.ConverterUtil
import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.vo.*
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