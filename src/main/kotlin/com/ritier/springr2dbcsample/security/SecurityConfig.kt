package com.ritier.springr2dbcsample.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.converter.RsaKeyConverters.x509
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun userDetailsService(): ReactiveUserDetailsService {
        val userDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("user")
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(userDetails)
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()


    @Bean
    fun configureSecurityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//        http.authorizeExchange {
//            x509()
//            it.anyExchange().authenticated().and().httpBasic().and().formLogin()
//        }
        http.cors().and()
            .anonymous().and()
            .authorizeExchange()
            .pathMatchers("api/auth/*")
            .permitAll()
        return http.build()
    }


}