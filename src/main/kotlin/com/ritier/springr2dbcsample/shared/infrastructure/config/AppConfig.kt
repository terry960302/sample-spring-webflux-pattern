package com.ritier.springr2dbcsample.shared.infrastructure.config

import com.ritier.springr2dbcsample.shared.infrastructure.config.properties.SecurityProperties
import com.ritier.springr2dbcsample.shared.infrastructure.config.properties.StorageProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    StorageProperties::class,
    SecurityProperties::class
)
class AppConfig {

}