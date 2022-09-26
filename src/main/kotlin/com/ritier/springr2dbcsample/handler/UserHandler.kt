package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN

@Component
class UserHandler {

    @Autowired
    private lateinit var userService: UserService

    fun createUser(request: ServerRequest): Mono<ServerResponse> =
        userService.createUser(request.bodyToMono(User::class.java))
            .flatMap { ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(it) }.onErrorResume { e ->
                Mono.just("Error" + e.message)
                    .flatMap { it -> ServerResponse.ok().contentType(TEXT_PLAIN).bodyValue(it) }
            }


    fun getUsers(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().contentType(APPLICATION_JSON).body(
            request.queryParam("nickname")
                .map { nickname ->
                    userService.findUsersByNickname(nickname)
                }.orElseGet {
                    userService.findAllUsers()
                }, User::class.java
        ).onErrorResume { e ->
            Mono.just("Error" + e.message).flatMap { ServerResponse.ok().contentType(TEXT_PLAIN).bodyValue(it) }
        }

    fun getUserById(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(userService.findUserById(request.pathVariable("id").toLong()), User::class.java).onErrorResume { e ->
            Mono.just("Error" + e.message).flatMap { ServerResponse.ok().contentType(TEXT_PLAIN).bodyValue(it) }
        }
}