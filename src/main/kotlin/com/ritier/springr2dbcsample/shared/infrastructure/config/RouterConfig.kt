package com.ritier.springr2dbcsample.shared.infrastructure.config

import com.ritier.springr2dbcsample.auth.adapter.`in`.web.AuthHandler
import com.ritier.springr2dbcsample.shared.constants.routes.RoutePaths
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.shared.exception.ErrorResponse
import com.ritier.springr2dbcsample.image.adapter.`in`.web.ImageHandler
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.PostingHandler
import com.ritier.springr2dbcsample.user.adapter.`in`.web.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*


import mu.KotlinLogging
import org.springframework.http.HttpStatus

@Configuration
class RouterConfig(
    private val authHandler: AuthHandler,
    private val userHandler: UserHandler,
    private val postingHandler: PostingHandler,
    private val imageHandler: ImageHandler,
) {

    private val logger = KotlinLogging.logger { }

    @Bean
    fun apiRouter(): RouterFunction<ServerResponse> {
        return coRouter {
            RoutePaths.API_VERSION.nest {
                // 인증 관련 라우트
                authRoutes()
                // 사용자 관련 라우트
                userRoutes()
                // 게시글 관련 라우트
                postingRoutes()
                // 이미지 관련 라우트
                imageRoutes()
            }
        }
            .filter(loggingFilter())
            .filter(errorHandlingFilter())
    }

    private fun CoRouterFunctionDsl.authRoutes() {
        RoutePaths.AUTH_PATH.nest {
            accept(APPLICATION_JSON).nest {
                POST(RoutePaths.SIGNUP_PATH, authHandler::signUp)
                POST(RoutePaths.LOGIN_PATH, authHandler::signIn)
            }
        }
    }

    private fun CoRouterFunctionDsl.userRoutes() {
        RoutePaths.USERS_PATH.nest {
            accept(APPLICATION_JSON).nest {
                GET("", userHandler::getUsers)
                GET("/{id}", userHandler::getUserById)
                GET(RoutePaths.EMAIL_EXISTS_PATH, userHandler::checkEmailExists)
                POST("", userHandler::createUser)
                PUT("/{id}", userHandler::updateUser)
                DELETE("/{id}", userHandler::deleteUser)

                // 사용자의 게시글 조회
                GET("/{id}${RoutePaths.POSTINGS_PATH}", userHandler::getUserPostings)
            }
        }
    }

    private fun CoRouterFunctionDsl.postingRoutes() {
        RoutePaths.POSTINGS_PATH.nest {
            accept(APPLICATION_JSON).nest {
                GET("", postingHandler::getPostings)
                GET("/{id}", postingHandler::getPostingById)
                GET(
                    "/{id}${RoutePaths.COMMENTS_PATH}",
                    postingHandler::getPostingComments
                )
            }
        }
    }

    private fun CoRouterFunctionDsl.imageRoutes() {
        RoutePaths.IMAGES_PATH.nest {
            accept(APPLICATION_JSON).nest {
                POST("", imageHandler::uploadImages)
            }
        }
    }

    private fun loggingFilter(): HandlerFilterFunction<ServerResponse, ServerResponse> {
        return HandlerFilterFunction { request, next ->
            logger.info { "요청: ${request.method()} ${request.path()}" }
            val startTime = System.currentTimeMillis()

            next.handle(request).doOnSuccess { response ->
                val duration = System.currentTimeMillis() - startTime
                logger.info { "응답: ${request.path()} - ${response.statusCode()} (${duration}ms)" }
            }
        }
    }

    fun errorHandlingFilter(): HandlerFilterFunction<ServerResponse, ServerResponse> {
        return HandlerFilterFunction { request, next ->
            next.handle(request).onErrorResume { ex ->
                logger.error(ex) { "요청 처리 중 예외 발생: ${request.path()}" }

                val (status, errorResponse: ErrorResponse) = when (ex) {
                    is AppException -> ex.errorCode.status to ex.toErrorResponse()
                    is IllegalArgumentException -> HttpStatus.BAD_REQUEST to ErrorResponse.from(ErrorCode.VALIDATION_FAILED)
                    else -> HttpStatus.INTERNAL_SERVER_ERROR to ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR)
                }

                ServerResponse.status(errorResponse.status)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(errorResponse)
            }
        }
    }

}

//@Configuration
//class RouterConfig {
//
//    @Autowired
//    private lateinit var userHandler: UserHandler
//
//    @Autowired
//    private lateinit var imageHandler: ImageHandler
//
//    @Autowired
//    private lateinit var postingHandler: PostingHandler
//
//    @Bean
//    fun apiRouter(): RouterFunction<ServerResponse> {
//        return coRouter {
//            "/api".nest {
//                (accept(APPLICATION_JSON) and "/users").nest {
//                    GET("/{id}") { userHandler.getUserById(it) }
//                    GET("") { userHandler.getUsers(it) }
//                    GET("", queryParam("nickname") { _: String? -> true }) { userHandler.getUsers(it) }
//                    POST("") { userHandler.createUser(it) }
//                    PUT("/{id}") { userHandler.updateUser(it) }
//                    DELETE("/{id}") { userHandler.deleteUser(it) }
//                }
//                "/images".nest {
//                    POST("", accept(MULTIPART_FORM_DATA)) { imageHandler.uploadImages(it) }
//                }
//                (accept(APPLICATION_JSON) and "/postings").nest {
//                    GET("") { postingHandler.getAllPostings(it) }
//                    GET("/{id}") { postingHandler.getPosting(it) }
//                    GET("/{id}/comments") { postingHandler.getAllCommentsByPostingId(it) }
//                }
//                "/auth".nest {
//                    POST("sign-up") { userHandler.signUp(it) }
//                    POST("sign-in") { userHandler.signIn(it) }
//                }
//            }
//        }
//    }
//}