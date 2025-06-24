package com.ritier.springr2dbcsample.infrastructure.persistence.converter

import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.model.User
import com.ritier.springr2dbcsample.domain.vo.user.Email
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@ReadingConverter
class UserReadConverter : Converter<Row, User> {
    override fun convert(row: Row): User {
        // 기본 사용자 정보 추출 (null 안전)
        val id = (row["user_id"] as? Number)?.toLong() ?: 0L
        val username = row["username"] as? String ?: ""
        val email = row["email"] as? String ?: ""
        val age = (row["age"] as? Number)?.toInt() ?: 0
        val createdAt = row["created_at"] as? LocalDateTime ?: LocalDateTime.now()
//
//        // 프로필 이미지 정보 추출 (nullable)
//        val profileImgId = (row["profile_img_id"] as? Number)?.toLong()
//        val profileImgUrl = row["profile_img_url"] as? String
//        val profileImgWidth = (row["profile_img_width"] as? Number)?.toInt()
//        val profileImgHeight = (row["profile_img_height"] as? Number)?.toInt()
//        val profileImgCreatedAt = row["profile_img_created_at"] as? LocalDateTime
//
//        // 프로필 이미지 객체 생성 (모든 필드가 있을 때만)
//        val profileImg = if (profileImgId != null &&
//            profileImgUrl != null &&
//            profileImgWidth != null &&
//            profileImgHeight != null &&
//            profileImgCreatedAt != null
//        ) {
//            Image(
//                id = ImageId(profileImgId),
//                url = profileImgUrl,
//                width = profileImgWidth,
//                height = profileImgHeight,
//                createdAt = profileImgCreatedAt
//            )
        val profileImg = Image.fromRowWithPrefix("profile_img_", row)
//        } else null

        return User(
            id = UserId(id),
            username = username,
            email = Email(email),
            age = age,
            createdAt = createdAt,
            profileImg = profileImg
        )
    }
}