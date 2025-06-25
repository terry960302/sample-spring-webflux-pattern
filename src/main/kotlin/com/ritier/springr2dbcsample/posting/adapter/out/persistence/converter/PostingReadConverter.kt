package com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.vo.Email
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@ReadingConverter
class PostingReadConverter : Converter<Row, Posting> {

    override fun convert(row: Row): Posting {
        // 기본 게시글 정보
        val id = PostingId(row.get("posting_id", Long::class.java)!!)
        val userId = UserId(row.get("user_id", Long::class.java)!!)
        val contents = row.get("contents", String::class.java)!!
        val createdAt = row.get("created_at", LocalDateTime::class.java)!!

        // 사용자 정보 (JOIN 결과)
        val user = extractUser(row)

        return Posting.create(
            id = id,
            userId = userId,
            contents = contents,
            createdAt = createdAt,
            user = user,
        )
    }

    private fun extractUser(row: Row): User? {
        val userId = row.get("user_id", Long::class.java) ?: return null
        val username = row.get("user_username", String::class.java) ?: return null
        val email = row.get("user_email", String::class.java) ?: return null
        val age = row.get("user_age", Int::class.java) ?: return null
        val userCreatedAt = row.get("user_created_at", LocalDateTime::class.java) ?: return null

        // 프로필 이미지 (optional)
        val profileImg = extractProfileImage(row)

        return User(
            id = UserId(userId),
            username = username,
            email = Email(email),
            age = age,
            createdAt = userCreatedAt,
            profileImg = profileImg
        )
    }

    private fun extractProfileImage(row: Row): Image? {
//        val imageId = row.get("profile_img_id", Long::class.java) ?: return null
//        val url = row.get("profile_img_url", String::class.java) ?: return null
//        val width = row.get("profile_img_width", Int::class.java) ?: return null
//        val height = row.get("profile_img_height", Int::class.java) ?: return null
//        val createdAt = row.get("profile_img_created_at", LocalDateTime::class.java) ?: return null

        return Image.fromRowWithPrefix("profile_img_", row)
    }
}
