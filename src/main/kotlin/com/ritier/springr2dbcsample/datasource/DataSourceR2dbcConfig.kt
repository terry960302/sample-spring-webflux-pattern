package com.ritier.springr2dbcsample.datasource

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder


@Configuration
@EnableJdbcRepositories
class DataSourceR2dbcConfig {

    @Autowired
    private lateinit var env: Environment

    @Bean
    fun connectionFactory(): ConnectionFactory {
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
    fun databaseClient(connectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.builder().connectionFactory(connectionFactory).build()


    @Bean
    fun initializer(): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("schema.sql"))
        initializer.setConnectionFactory(connectionFactory())
        initializer.setDatabasePopulator(resourceDatabasePopulator)
        return initializer
    }

}