package io.spring.deepdive

import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.coroutine.function.client.body
import org.springframework.web.coroutine.function.client.DefaultCoroutineWebClient
import org.springframework.web.reactive.function.client.WebClient


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HtmlTests(@LocalServerPort val port: Int) {

    private val client =  DefaultCoroutineWebClient(WebClient.create("http://localhost:$port"))

    @Test
    fun `Assert content on blog page`() = runBlocking<Unit> {
        val body = client.get().uri("/").retrieve().body<String>()
        assertThat(body)
                .contains("Reactor Bismuth is out")
                .contains("September 28th")
                .contains("Sebastien")
                .doesNotContain("brand-new generation")
    }

    @Test
    fun `Assert content on blog post page`() = runBlocking<Unit> {
        val body = client.get().uri("/spring-framework-5-0-goes-ga").retrieve().body<String>()
        assertThat(body)
                .contains("Spring Framework 5.0 goes GA")
                .contains("Dear Spring community")
                .contains("brand-new generation")
                .contains("Juergen")
                .doesNotContain("Sebastien")
    }

}
