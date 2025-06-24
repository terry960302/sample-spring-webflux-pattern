package com.ritier.springr2dbcsample.common.config

import com.ritier.springr2dbcsample.common.config.properties.StorageProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(StorageProperties::class)
class AppConfig{

}