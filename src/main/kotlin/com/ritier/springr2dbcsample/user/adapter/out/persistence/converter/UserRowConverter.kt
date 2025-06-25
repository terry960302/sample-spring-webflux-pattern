package com.ritier.springr2dbcsample.user.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.vo.Email
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
object UserRowConverter {

    fun convertFromRow(row: Map<String, *>): User? {
        val userId = row["user_id"]?.toString()?.toLongOrNull() ?: return null
        val username = row["username"]?.toString() ?: return null
        val email = row["email"]?.toString() ?: return null
        val age = row["age"]?.toString()?.toIntOrNull() ?: return null

        val profileImg = ProfileImageRowConverter.convertFromRow(row)

        return User(
            id = UserId(userId),
            username = username,
            email = Email(email), // 기존 쿼리 구조 유지
            age = age,
            createdAt = LocalDateTime.now(), // 기존 쿼리 구조 유지
            profileImg = profileImg
        )
    }
}
