package io.spring.deepdive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

class HtmlTests : AbstractIntegrationTests() {

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
        client.get().uri("/spring-framework-5-0-goes-ga").retrieve().bodyToMono<String>()
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
