package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.entity.mapper.UserMapper
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


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

    fun save(user: User): Mono<User> =
        databaseClient.sql("CREATE TABLE users COLUMNS(nickname, age) VALUES (:nickname, :age)")
            .bind("nickname", user.nickname)
            .bind("age", user.age)
            .map(userMapper::apply)
            .one()

    fun findAll(): Flux<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id")
            .map(userMapper::apply)
            .all()

    fun findById(id: Long): Mono<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.user_id = :id")
            .bind("id", id)
            .map(userMapper::apply)
            .one()

    fun findByNickname(nickname: String): Flux<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.nickname = :nickname")
            .bind("nickname", nickname)
            .map(userMapper::apply)
            .all()


    fun deleteById(id: Long): Mono<Void> =
        databaseClient.sql("DELETE FROM users WHERE user_id = :id")
            .bind("id", id)
            .then()
}
