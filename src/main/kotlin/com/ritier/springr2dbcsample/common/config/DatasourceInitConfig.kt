package com.ritier.springr2dbcsample.common.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

// 초기 스키마 설정 및 데이터를 로딩하는데 사용됩니다. yml 값 조정을 통해 설정
@Configuration
@ConditionalOnProperty( // 특정 조건일때만 로드
    name = ["spring.sql.init.mode"],
    havingValue = "always"
)
class DatabaseInitConfig {

    companion object {
        private const val CREATE_SCHEMAS_SQL: String = "datasource/createSchema.sql";
        private const val INSERT_ROWS_SQL: String = "datasource/insertRows.sql";
    }

    @Bean
    fun connectionFactoryInitializer(
        connectionFactory: ConnectionFactory
    ): ConnectionFactoryInitializer {
        return ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(createDatabasePopulator())
        }
    }

    private fun createDatabasePopulator(): ResourceDatabasePopulator {
        return ResourceDatabasePopulator().apply {
            addScript(ClassPathResource(CREATE_SCHEMAS_SQL))
        }
    }
}