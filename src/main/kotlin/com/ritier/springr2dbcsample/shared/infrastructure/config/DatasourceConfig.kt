package com.ritier.springr2dbcsample.shared.infrastructure.config

import com.ritier.springr2dbcsample.shared.infrastructure.config.properties.R2dbcProperties
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter.PostingImageReadConverter
import com.ritier.springr2dbcsample.user.adapter.out.persistence.converter.UserReadConverter
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.core.DatabaseClient


@Configuration
@EnableR2dbcRepositories
@EnableConfigurationProperties(R2dbcProperties::class)
class DatasourceConfig(
    private val r2dbcProperties: R2dbcProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactoryBuilder.withOptions(
            createConnectionOptions()
        ).build()
    }

    @Bean
    fun r2dbcDatabaseClient(connectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.builder().connectionFactory(connectionFactory).build()

    private fun createConnectionOptions(): ConnectionFactoryOptions.Builder {
        return builder()
            .option(DRIVER, r2dbcProperties.driver)
            .option(HOST, r2dbcProperties.host)
            .option(PROTOCOL, r2dbcProperties.protocol)
            .option(DATABASE, r2dbcProperties.database)
            .option(PORT, r2dbcProperties.port)
            .option(USER, r2dbcProperties.username)
            .option(PASSWORD, r2dbcProperties.password)
    }

    override fun getCustomConverters(): List<Any> {
        return listOf(
            UserReadConverter(),
            PostingImageReadConverter(),
        )
    }

    override fun r2dbcCustomConversions(): R2dbcCustomConversions {
        return R2dbcCustomConversions.of(
            PostgresDialect.INSTANCE,
            listOf(
                UserReadConverter(),
                PostingImageReadConverter(),
            )
        )
    }
}