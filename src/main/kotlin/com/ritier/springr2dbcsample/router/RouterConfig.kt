package com.ritier.springr2dbcsample.router

import com.ritier.springr2dbcsample.handler.UserHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.route


@Configuration
class RouterConfig {

    @Autowired
    private lateinit var userHandler: UserHandler

    @Bean
    fun apiRouter(): RouterFunction<ServerResponse> {
        return coRouter {
            "/api".nest {
                "/user".nest {
                    GET("/{id}", accept(APPLICATION_JSON)) { userHandler.getUserById(it) }
                    GET("", accept(APPLICATION_JSON)) { userHandler.getUsers(it) }
                    GET("", queryParam("nickname") { _: String? -> true }) { userHandler.getUsers(it) }
                    POST("") { userHandler.createUser(it) }
                    PUT("/{id}") { userHandler.updateUser(it) }
                    DELETE("/{id}") { userHandler.deleteUser(it) }
                }
            }
        }
    }

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