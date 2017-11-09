package io.spring.deepdive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

import org.springframework.web.client.getForObject

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HtmlTests(@LocalServerPort port: Int, @Autowired builder: RestTemplateBuilder) {

    // We don't use TestRestTemplate because of Spring Boot issues #10761 and #8062
    private val restTemplate = builder.rootUri("http://localhost:$port").build()

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
