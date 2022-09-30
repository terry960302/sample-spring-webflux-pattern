package com.ritier.springr2dbcsample.entity.converter

import com.ritier.springr2dbcsample.entity.Comment
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class CommentReadConverter : Converter<Row, Comment>{
    override fun convert(row: Row): Comment {
        return Comment(
            id = row.get("comment_id").toString().toLong(),
//            user = userMapper.apply(row, metadata),
            userId = row.get("user_id").toString().toLong(),
            postingId = row.get("posting_id").toString().toLong(),
//            posting = postingMapper.apply(row, metadata),
            contents = row.get("contents").toString(),
            createdAt = com.ritier.springr2dbcsample.util.Converter.convertStrToLocalDateTime(row.get("created_at").toString()),
        )
    }
}