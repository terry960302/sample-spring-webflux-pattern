package com.ritier.springr2dbcsample.infrastructure.persistence.converter

import com.ritier.springr2dbcsample.common.util.ConverterUtil
import com.ritier.springr2dbcsample.domain.model.Comment
import com.ritier.springr2dbcsample.domain.vo.posting.CommentId
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import io.r2dbc.spi.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class CommentReadConverter : Converter<Row, Comment> {

    @Autowired
    private lateinit var userReadConverter: UserReadConverter
    override fun convert(row: Row): Comment {
        return Comment(
            id = CommentId(row.get("comment_id").toString().toLong()),
            user = userReadConverter.convert(row),
            userId = UserId(row.get("user_id").toString().toLong()),
            postingId = PostingId(row.get("posting_id").toString().toLong()),
            contents = row.get("contents").toString(),
            createdAt = ConverterUtil.convertStrToLocalDateTime(row.get("created_at").toString()),
        )
    }
}