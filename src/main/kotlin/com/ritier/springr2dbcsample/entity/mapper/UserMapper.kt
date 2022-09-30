package com.ritier.springr2dbcsample.entity.mapper

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.User
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.stereotype.Component
import java.sql.Date
import java.util.function.BiFunction
import kotlin.reflect.typeOf

@Component
class UserMapper : BiFunction<Row, RowMetadata, User> {
    override fun apply(row: Row, metadata: RowMetadata): User {
        return User(
            id = row.get("user_id")!!.toString().toLong(),
            nickname = row.get("nickname", String::class.java)!!,
            age = row.get("age")!!.toString().toInt(),
            profileImgId = row.get("profile_img_id")!!.toString().toLong(),
            profileImg = Image(
                id = row.get("image_id")!!.toString().toLong(),
                url = row.get("url", String::class.java)!!,
                width = row.get("width")!!.toString().toInt(),
                height = row.get("height")!!.toString().toInt(),
                createdAt = row.get("created_at", String::class.java)!!,
            )
        )
    }
}