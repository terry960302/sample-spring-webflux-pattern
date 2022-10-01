package com.ritier.springr2dbcsample.handler

import com.ritier.springr2dbcsample.service.PostingService
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class PostingHandlerTest{
    @MockBean
    private lateinit var postingService: PostingService
    private lateinit var webTestClient : WebTestClient

}