package io.spring.deepdive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient

import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HtmlTests(@LocalServerPort port: Int) {

    // TODO Migrate to WebTestClient when https://youtrack.jetbrains.com/issue/KT-5464 will be fixed
    protected val client = WebClient.create("http://localhost:$port")

    @Test
    fun `Assert content on blog page`() {
        client.get().uri("/").retrieve().bodyToMono<String>()
                .test()
                .consumeNextWith {
                    assertThat(it)
                            .contains("Reactor Bismuth is out")
                            .contains("September 28th")
                            .contains("Sebastien")
                            .doesNotContain("brand-new generation")
                }.verifyComplete()
    }

    @Test
    fun `Assert content on blog post page`() {
        client.get().uri("/article/spring-framework-5-0-goes-ga").retrieve().bodyToMono<String>()
                .test()
                .consumeNextWith {
                    assertThat(it)
                            .contains("Spring Framework 5.0 goes GA")
                            .contains("Dear Spring community")
                            .contains("brand-new generation")
                            .contains("Juergen")
                            .doesNotContain("Sebastien")
                }.verifyComplete()
    }

}
