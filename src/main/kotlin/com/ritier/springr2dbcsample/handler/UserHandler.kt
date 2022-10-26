package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.dto.UserDto
import com.ritier.springr2dbcsample.dto.auth.AuthRequest
import com.ritier.springr2dbcsample.dto.auth.AuthResponse
import com.ritier.springr2dbcsample.dto.auth.SignUpRequest
import com.ritier.springr2dbcsample.dto.common.CommonError
import com.ritier.springr2dbcsample.dto.common.ErrorResponseDto
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.web.reactive.function.server.*
import java.util.concurrent.Flow
import kotlin.math.log

@Component
class UserHandler {

    @Autowired
    private lateinit var userService: UserService

    suspend fun signUp(request: ServerRequest) : ServerResponse{
        return try {
            val reqBody = request.awaitBody<SignUpRequest>()
            val res : AuthResponse = userService.signUp(reqBody)
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(res)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun signIn(request: ServerRequest) : ServerResponse {
        return try {
            val reqBody = request.awaitBody<AuthRequest>()
            val res : AuthResponse = userService.signIn(reqBody.email, reqBody.password)
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(res)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun createUser(request: ServerRequest): ServerResponse {
        return try {
            val reqBody = request.awaitBody<User>()
            val user = userService.createUser(UserDto.from(reqBody))
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(user)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun updateUser(request: ServerRequest): ServerResponse {
        return try {
            val userId = request.pathVariable("id").toLong()
            val reqBody = request.awaitBody<User>()
            val user = userService.updateUser(userId, reqBody)
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(user)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun deleteUser(request: ServerRequest): ServerResponse {
        return try {
            val userId = request.pathVariable("id").toLong()
            val user = userService.deleteUser(userId)
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(user)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun getUsers(request: ServerRequest): ServerResponse {
        return try {
            val queryParam = request.queryParam("nickname")
            val users = if (queryParam.isEmpty) {
                userService.findAllUsers()
            } else {
                val nickname = queryParam.get();
                userService.findUsersByNickname(nickname)
            }
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyAndAwait(users)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }

    suspend fun getUserById(request: ServerRequest): ServerResponse {
        return try {
            val userId = request.pathVariable("id").toLong()
            val user = userService.findUserById(userId)
            ServerResponse.ok().contentType(APPLICATION_JSON).bodyValueAndAwait(user)
        } catch (e: Error) {
            println("Error : ${e.message}")
            val err = ErrorResponseDto(
                error = CommonError(
                    code = 500,
                    message = e.message.toString(),
                    type = "",
                )
            )
            ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(err)
        }
    }
}