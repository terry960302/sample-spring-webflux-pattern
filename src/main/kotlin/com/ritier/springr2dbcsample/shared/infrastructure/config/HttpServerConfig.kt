package com.ritier.springr2dbcsample.shared.infrastructure.config

import com.ritier.springr2dbcsample.shared.infrastructure.config.properties.ServerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer

@Configuration
@EnableConfigurationProperties(ServerProperties::class)
class HttpServerConfig(
    private val serverProperties: ServerProperties
) {

    @Bean
    fun httpServer(routerFunction: RouterFunction<*>): DisposableServer {
        val httpHandler: HttpHandler = RouterFunctions.toHttpHandler(routerFunction)
        val adapter = ReactorHttpHandlerAdapter(httpHandler)

        return HttpServer.create()
            .host(serverProperties.host)
            .port(serverProperties.port)
            .handle(adapter)
            .bindNow()
    }
}
