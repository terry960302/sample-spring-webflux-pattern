package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.entity.mapper.UserMapper
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.stereotype.Repository
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepositoryDao : ReactiveCrudRepository<User, Long> {
    fun findByNickname(nickname: String): Flux<User>
}

@Repository
class UserRepository : UserRepositoryDao {
    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun findAll(): Flux<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id")
            .map(userMapper::apply)
            .all()

    override fun findAllById(ids: MutableIterable<Long>): Flux<User> {
        TODO("Not yet implemented")
    }

    override fun findAllById(idStream: Publisher<Long>): Flux<User> {
        TODO("Not yet implemented")
    }

    override fun count(): Mono<Long> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Publisher<Long>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun delete(entity: User): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<User>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entityStream: Publisher<out User>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Mono<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.user_id = :id")
            .bind("id", id)
            .map(userMapper::apply)
            .one()

    override fun findByNickname(nickname: String): Flux<User> =
        databaseClient.sql("SELECT * FROM users as u JOIN images as i on i.image_id = u.profile_img_id WHERE u.nickname = :nickname")
            .bind("nickname", nickname)
            .map(userMapper::apply)
            .all()

    override fun <S : User?> save(entity: S): Mono<S> {
        TODO("Not yet implemented")
    }

    override fun <S : User?> saveAll(entities: MutableIterable<S>): Flux<S> {
        TODO("Not yet implemented")
    }

    override fun <S : User?> saveAll(entityStream: Publisher<S>): Flux<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Publisher<Long>): Mono<User> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Publisher<Long>): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}
