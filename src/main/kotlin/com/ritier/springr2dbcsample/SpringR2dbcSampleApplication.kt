package com.ritier.springr2dbcsample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class SpringR2dbcSampleApplication

fun main(args: Array<String>) {
	runApplication<SpringR2dbcSampleApplication>(*args)
}
