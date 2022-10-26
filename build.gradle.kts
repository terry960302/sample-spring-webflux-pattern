import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.ritier"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.projectlombok:lombok:1.18.22")
	implementation("org.projectlombok:lombok:1.18.22")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	// kotest
	testImplementation("io.kotest:kotest-runner-junit5:5.4.0")
	testImplementation("io.kotest:kotest-assertions-core:5.4.0")
	testImplementation("io.kotest:kotest-property:5.4.0")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")

	// mockk(for mocking instances in test)
	testImplementation("io.mockk:mockk:1.13.2")

	// r2dbc for postgresql
	implementation("io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE")
//	implementation("org.springframework.data:spring-data-jdbc:2.4.3")
	runtimeOnly("io.r2dbc:r2dbc-postgresql")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

	// gcp
	implementation(platform("com.google.cloud:libraries-bom:26.1.2"))
	implementation("com.google.cloud:google-cloud-storage")

	// logger
	implementation(group = "io.github.microutils", name = "kotlin-logging-jvm", version = "2.0.6")

	// security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.9.1") // jwt
	implementation("org.mindrot:jbcrypt:0.4") // hash
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

//tasks.withType<Test> {
//	useJUnitPlatform()
//}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}