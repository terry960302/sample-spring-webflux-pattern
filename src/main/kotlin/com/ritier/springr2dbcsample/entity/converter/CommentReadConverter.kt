package com.ritier.springr2dbcsample.entity.converter

import com.ritier.springr2dbcsample.entity.Comment
import io.r2dbc.spi.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class CommentReadConverter : Converter<Row, Comment>{

    @Autowired
    private lateinit var userReadConverter: UserReadConverter
    override fun convert(row: Row): Comment {
        return Comment(
            id = row.get("comment_id").toString().toLong(),
            user = userReadConverter.convert(row),
            userId = row.get("user_id").toString().toLong(),
            postingId = row.get("posting_id").toString().toLong(),
            contents = row.get("contents").toString(),
            createdAt = com.ritier.springr2dbcsample.util.ConverterUtil.convertStrToLocalDateTime(row.get("created_at").toString()),
        )
    }
}