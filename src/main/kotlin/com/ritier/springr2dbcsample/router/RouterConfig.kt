package com.ritier.springr2dbcsample.router

import com.ritier.springr2dbcsample.handler.ImageHandler
import com.ritier.springr2dbcsample.handler.PostingHandler
import com.ritier.springr2dbcsample.handler.UserHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.web.reactive.function.server.*


@Configuration
class RouterConfig {

    @Autowired
    private lateinit var userHandler: UserHandler

    @Autowired
    private lateinit var imageHandler: ImageHandler

    @Autowired
    private lateinit var postingHandler: PostingHandler

    @Bean
    fun apiRouter(): RouterFunction<ServerResponse> {
        return coRouter {
            "/api".nest {
                (accept(APPLICATION_JSON) and "/users").nest {
                    GET("/{id}") { userHandler.getUserById(it) }
                    GET("") { userHandler.getUsers(it) }
                    GET("", queryParam("nickname") { _: String? -> true }) { userHandler.getUsers(it) }
                    POST("") { userHandler.createUser(it) }
                    PUT("/{id}") { userHandler.updateUser(it) }
                    DELETE("/{id}") { userHandler.deleteUser(it) }
                }
                "/images".nest {
                    POST("", accept(MULTIPART_FORM_DATA)) { imageHandler.uploadImages(it) }
                }
                (accept(APPLICATION_JSON) and "/postings").nest {
                    GET("") { postingHandler.getAllPostings(it) }
                    GET("/{id}") { postingHandler.getPosting(it) }
                    GET("/{id}/comments") { postingHandler.getAllCommentsByPostingId(it) }
                }
            }
        }
    }

// reactor pattern
//    @Bean
//    fun routerFunction(): RouterFunction<*> {
//        val userRoute: RouterFunction<ServerResponse> = route()
//            .path("/user") { builder ->
//                builder
//                    .GET("/{id}", accept(APPLICATION_JSON)) { userHandler.getUserById(it) }
//                    .GET("", accept(APPLICATION_JSON)) { userHandler.getUsers(it) }
//                    .GET("", queryParam("nickname") { _: String? -> true }) { userHandler.getUsers(it) }
//                    .POST("") { userHandler.createUser(it) }
//                    .PUT("/{id}") { userHandler.updateUser(it) }
//                    .DELETE("/{id}") { userHandler.deleteUser(it) }
//            }
//            .build()
//
//        return RouterFunctions.nest(
//            path("/api"),
//            userRoute
//        )
//    }
}