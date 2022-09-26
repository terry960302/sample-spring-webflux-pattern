package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.stereotype.Repository
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux


@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    @Query("SELECT u.* FROM user u WHERE u.nickname = :nickname")
    fun findByNickname(nickname: String): Flux<User>
}