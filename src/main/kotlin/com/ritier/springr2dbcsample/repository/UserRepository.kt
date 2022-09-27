package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.entity.mapper.UserMapper
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository


// 관계매핑이 필요한 경우라면 굳이 필요없음.
//interface UserRepositoryDao : ReactiveCrudRepository<User, Long> {
//    fun findByNickname(nickname: String): Flux<User>
//}

@Repository
class UserRepository {
    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @Autowired
    private lateinit var userMapper: UserMapper

    suspend fun count(): Long =
        databaseClient.sql("SELECT COUNT(*) FROM users")
            .map { it -> it.get("count").toString().toLong() }
            .awaitOne()

    suspend fun save(user: User): User =
        databaseClient.sql("CREATE TABLE users COLUMNS(nickname, age) VALUES (:nickname, :age)")
            .bind("nickname", user.nickname)
            .bind("age", user.age)
            .map(userMapper::apply)
            .awaitOne()

    suspend fun findAll(): Flow<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id")
            .map(userMapper::apply)
            .flow()

    suspend fun findById(id: Long): User =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.user_id = :id")
            .bind("id", id)
            .map(userMapper::apply)
            .awaitOne()

    suspend fun findByNickname(nickname: String): Flow<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.nickname = :nickname")
            .bind("nickname", nickname)
            .map(userMapper::apply)
            .flow()


    suspend fun deleteById(id: Long): Unit =
        databaseClient.sql("DELETE FROM users WHERE user_id = :id")
            .bind("id", id)
            .await()
}
