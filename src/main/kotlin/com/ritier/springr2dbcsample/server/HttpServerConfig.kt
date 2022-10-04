package com.ritier.springr2dbcsample.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import reactor.netty.http.server.HttpServer

@Configuration
class HttpServerConfig {

    @Autowired
    private lateinit var env: Environment

    @Bean
    fun httpServer(routerFunction: RouterFunction<*>): HttpServer {
        val httpHandler = RouterFunctions.toHttpHandler(routerFunction)
        val adapter = ReactorHttpHandlerAdapter(httpHandler)
        return HttpServer.create()
            .host(env.getProperty("spring.server.host")!!.toString())
            .port(env.getProperty("spring.server.port")!!.toInt())
            .handle(adapter)
    }
}