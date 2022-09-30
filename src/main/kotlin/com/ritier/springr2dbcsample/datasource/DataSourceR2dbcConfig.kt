package com.ritier.springr2dbcsample.datasource

import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient


@Configuration
class DataSourceR2dbcConfig: AbstractR2dbcConfiguration() {

    @Autowired
    private lateinit var env: Environment

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactoryBuilder.withOptions(
            builder().option(DRIVER, "postgresql")
                .option(HOST, env.getProperty("spring.r2dbc.host")!!)
                .option(PROTOCOL, env.getProperty("spring.r2dbc.protocol")!!)
                .option(DATABASE, env.getProperty("spring.r2dbc.database")!!)
                .option(PORT, env.getProperty("spring.r2dbc.port")!!.toInt())
                .option(USER, env.getProperty("spring.r2dbc.username")!!)
                .option(PASSWORD, env.getProperty("spring.r2dbc.password")!!)
        ).build()
    }

    @Bean
     fun r2dbcDatabaseClient(connectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.builder().connectionFactory(connectionFactory).build()


    // schema generation (R2DBC에서 공식적으로 지원하지 않아 만듬)
    @Bean
    fun initializer(): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("createSchema.sql"))
//        resourceDatabasePopulator.addScript(ClassPathResource("insertSchema.sql")) // 중복된 데이터 쌓이는 거 방지를 위해 주석
        initializer.setConnectionFactory(connectionFactory())
        initializer.setDatabasePopulator(resourceDatabasePopulator)
        return initializer
    }

}