package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.repository.UserRepository
import com.ritier.springr2dbcsample.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun createUser(user: Mono<User>): Mono<User> = user.flatMap { this.userRepository.save(it) }

    fun findAllUsers(): Flux<User> = this.userRepository.findAll()

    fun findUserById(id: Long): Mono<User> = this.userRepository.findById(id);

    fun findUsersByNickname(nickname: String): Flux<User> = this.userRepository.findByNickname(nickname)

    fun updateUser(id: Long, user: Mono<User>): Mono<User> = this.userRepository
        .findById(id).flatMap { it ->
            this.userRepository.save(
                it.copy(
                    nickname = it.nickname,
                    age = it.age
                )
            )
        }

    fun deleteUser(id: Long): Mono<Void> = this.userRepository.deleteById(id)
}