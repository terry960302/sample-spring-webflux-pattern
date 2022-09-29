package com.ritier.springr2dbcsample.entity.mapper

import com.ritier.springr2dbcsample.entity.Image
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ImageMapper : BiFunction<Row, RowMetadata, Image> {
    override fun apply(row: Row, metadata: RowMetadata): Image {
        return Image(
            id = row.get("image_id").toString().toLong(),
            url = row.get("url").toString(),
            width = row.get("width").toString().toInt(),
            height = row.get("height").toString().toInt(),
            createdAt = row.get("created_at").toString(),
        )
    }
}