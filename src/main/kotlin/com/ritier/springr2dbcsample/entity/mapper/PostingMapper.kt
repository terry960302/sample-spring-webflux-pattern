package com.ritier.springr2dbcsample.entity.mapper

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.util.Converter
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class PostingMapper: BiFunction<Row, RowMetadata, Posting> {
    @Autowired
    private lateinit var userMapper : UserMapper
//    @Autowired
//    private lateinit var imageMapper : ImageMapper

    override fun apply(row: Row, metadata: RowMetadata): Posting {
        return Posting(
            id = row.get("posting_id")!!.toString().toLong(),
            userId = row.get("user_id")!!.toString().toLong(),
            user = userMapper.apply(row, metadata),
            contents = row.get("contents")!!.toString(),
            createdAt = Converter.convertStrToLocalDateTime(row.get("created_at").toString()),
            images = arrayListOf(), // TODO : mapper 로 관리가 힘듬, 좀 더 효율적인 방안을 고민할 필요가 있음...
            comments = arrayListOf(), // TODO : mapper 로 관리가 힘듬, 좀 더 효율적인 방안을 고민할 필요가 있음...
        )
    }
}