package com.ritier.springr2dbcsample.entity.mapper

import com.ritier.springr2dbcsample.entity.Comment
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.util.Converter
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class CommentMapper : BiFunction<Row, RowMetadata, Comment>{
    @Autowired
    private lateinit var userMapper: UserMapper
    @Autowired
    private lateinit var postingMapper: PostingMapper

    override fun apply(row: Row, metadata: RowMetadata): Comment {
        return Comment(
            id = row.get("comment_id").toString().toLong(),
//            user = userMapper.apply(row, metadata),
            userId = row.get("user_id").toString().toLong(),
            postingId = row.get("posting_id").toString().toLong(),
//            posting = postingMapper.apply(row, metadata),
            contents = row.get("contents").toString(),
            createdAt = Converter.convertStrToLocalDateTime(row.get("created_at").toString()),
        )
    }

}