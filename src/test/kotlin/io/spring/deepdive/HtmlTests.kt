package io.spring.deepdive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.web.client.getForObject

class HtmlTests : AbstractIntegrationTests() {

    @Test
    fun `Assert content on blog page`() {
        val body = restTemplate.getForObject<String>("/")
        assertThat(body)
                .contains("Reactor Bismuth is out")
                .contains("September 28th")
                .contains("Sebastien")
                .doesNotContain("brand-new generation")
    }

    @Test
    fun `Assert content on blog post page`() {
        val body = restTemplate.getForObject<String>("/spring-framework-5-0-goes-ga")
        assertThat(body)
                .contains("Spring Framework 5.0 goes GA")
                .contains("Dear Spring community")
                .contains("brand-new generation")
                .contains("Juergen")
                .doesNotContain("Sebastien")
    }

}
