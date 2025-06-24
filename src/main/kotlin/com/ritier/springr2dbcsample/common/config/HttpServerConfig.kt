package com.ritier.springr2dbcsample.common.config

import com.ritier.springr2dbcsample.common.config.properties.ServerProperties
import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import javax.annotation.PreDestroy

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
