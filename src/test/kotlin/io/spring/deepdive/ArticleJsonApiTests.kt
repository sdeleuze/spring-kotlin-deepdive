/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.spring.deepdive

import java.time.LocalDateTime

import io.spring.deepdive.model.Article
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.coroutine.function.client.body
import org.springframework.web.coroutine.function.client.DefaultCoroutineWebClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ArticleJsonApiTests(@LocalServerPort val port: Int) {

    private val client =  DefaultCoroutineWebClient(WebClient.create("http://localhost:$port"))

    @Test
    fun `Assert findAll JSON API is parsed correctly and contains 3 elements`() = runBlocking<Unit> {
        val articles = client.get().uri("/api/article/").retrieve().body<List<Article>>()
        assertThat(articles).hasSize(3)
    }

    @Test
    fun `Verify findOne JSON API`() = runBlocking<Unit> {
        val article = client.get().uri("http://localhost:$port/api/article/reactor-bismuth-is-out").retrieve().body<Article>()!!
        assertThat(article.title).isEqualTo("Reactor Bismuth is out")
        assertThat(article.headline).startsWith("It is my great pleasure to")
        assertThat(article.content).startsWith("With the release of")
        assertThat(article.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
        assertThat(article.author).isEqualTo("simonbasle")
    }

    @Test
    fun `Verify findOne JSON API with Markdown converter`() = runBlocking<Unit> {
        val article = client.get().uri("http://localhost:$port/api/article/reactor-bismuth-is-out?converter=markdown").retrieve().body<Article>()!!
        assertThat(article.title).startsWith("Reactor Bismuth is out")
        assertThat(article.headline).doesNotContain("**3.1.0.RELEASE**").contains("<strong>3.1.0.RELEASE</strong>")
        assertThat(article.content).doesNotContain("[Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)").contains("<a href=\"https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga\">")
        assertThat(article.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
        assertThat(article.author).isEqualTo("simonbasle")
    }

    @Test
    fun `Verify findOne JSON API with invalid converter`() = runBlocking<Unit> {
        var ex: Throwable? = null
        try {
            client.get().uri("http://localhost:$port/api/article/reactor-bismuth-is-out?converter=foo").retrieve().body<Article>()
        } catch (e: WebClientResponseException) {
            ex = e
        }
        assertThat(ex).isInstanceOf(WebClientResponseException::class.java)
    }

}
