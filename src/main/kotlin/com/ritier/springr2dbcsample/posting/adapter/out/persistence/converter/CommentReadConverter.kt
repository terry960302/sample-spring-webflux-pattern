package com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.shared.util.ConverterUtil
import com.ritier.springr2dbcsample.user.adapter.out.persistence.converter.UserReadConverter
import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.posting.domain.vo.CommentId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
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