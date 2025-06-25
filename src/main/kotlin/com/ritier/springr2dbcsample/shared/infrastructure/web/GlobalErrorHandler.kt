package com.ritier.springr2dbcsample.shared.infrastructure.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.shared.exception.ErrorResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalErrorHandler(private val objectMapper: ObjectMapper) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response

        if (response.isCommitted) {
            // 이미 응답이 커밋되었으면 처리 불가
            return Mono.error(ex)
        }

        val status = if (ex is AppException) {
            HttpStatus.resolve(ex.errorCode.status) ?: HttpStatus.INTERNAL_SERVER_ERROR
        } else {
            HttpStatus.INTERNAL_SERVER_ERROR
        }

        val errorResponse = when (ex) {
            is AppException -> ErrorResponse.from(ex.errorCode)
            else -> ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR)
        }

        return try {
            val bytes = objectMapper.writeValueAsBytes(errorResponse)
            val buffer = response.bufferFactory().wrap(bytes)

            response.statusCode = status
            response.headers.contentType = MediaType.APPLICATION_JSON

            response.writeWith(Mono.just(buffer))
        } catch (e: Exception) {
            // 직렬화 실패 시 fallback
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
            response.writeWith(Mono.empty())
        }
    }
}