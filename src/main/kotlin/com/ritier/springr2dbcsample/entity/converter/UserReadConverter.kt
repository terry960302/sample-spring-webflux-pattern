package com.ritier.springr2dbcsample.entity.converter

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.util.ConverterUtil
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class UserReadConverter : Converter<Row, User> {
    override fun convert(row: Row): User {
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
                createdAt = ConverterUtil.convertStrToLocalDateTime(row.get("created_at", String::class.java)!!),
            )
        )
    }
}