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

import io.spring.deepdive.model.Post
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.coroutine.function.client.body
import org.springframework.web.coroutine.function.client.DefaultCoroutineWebClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostJsonApiTests(@LocalServerPort val port: Int) {

    private val client =  DefaultCoroutineWebClient(WebClient.create("http://localhost:$port"))

    @Test
    fun `Assert findAll JSON API is parsed correctly and contains 3 elements`() = runBlocking<Unit> {
        val posts = client.get().uri("/api/post/").retrieve().body<List<Post>>()
        assertThat(posts).hasSize(3)
    }

    @Test
    fun `Verify findOne JSON API`() = runBlocking<Unit> {
        val post = client.get().uri("http://localhost:$port/api/post/reactor-bismuth-is-out").retrieve().body<Post>()!!
        assertThat(post.title).isEqualTo("Reactor Bismuth is out")
        assertThat(post.headline).startsWith("It is my great pleasure to")
        assertThat(post.content).startsWith("With the release of")
        assertThat(post.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
        assertThat(post.author.firstname).isEqualTo("Simon")
    }

    @Test
    fun `Verify findOne JSON API with Markdown converter`() = runBlocking<Unit> {
        val post = client.get().uri("http://localhost:$port/api/post/reactor-bismuth-is-out?converter=markdown").retrieve().body<Post>()!!
        assertThat(post.title).startsWith("Reactor Bismuth is out")
        assertThat(post.headline).doesNotContain("**3.1.0.RELEASE**").contains("<strong>3.1.0.RELEASE</strong>")
        assertThat(post.content).doesNotContain("[Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)").contains("<a href=\"https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga\">")
        assertThat(post.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
        assertThat(post.author.firstname).isEqualTo("Simon")
    }

    @Test
    fun `Verify findOne JSON API with invalid converter`() = runBlocking<Unit> {
        var ex: Throwable? = null
        try {
            client.get().uri("http://localhost:$port/api/post/reactor-bismuth-is-out?converter=foo").retrieve().body<Post>()
        } catch (e: WebClientResponseException) {
            ex = e
        }
        assertThat(ex).isInstanceOf(WebClientResponseException::class.java)
    }

}
