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
import io.spring.deepdive.model.PostEvent
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

class PostJsonApiTests : AbstractIntegrationTests() {

    @Test
    fun `Assert findAll JSON API is parsed correctly and contains 3 elements`() {
        client.get().uri("/api/post/").retrieve().bodyToFlux<Post>()
                .test()
                .expectNextCount(3)
                .verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API`() {
        client.get().uri("/api/post/reactor-bismuth-is-out").retrieve().bodyToMono<Post>()
                .test()
                .consumeNextWith {
                    assertThat(it.title).isEqualTo("Reactor Bismuth is out")
                    assertThat(it.headline).startsWith("It is my great pleasure to")
                    assertThat(it.content).startsWith("With the release of")
                    assertThat(it.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
                    assertThat(it.author).isEqualTo("simonbasle")
                }.verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API with Markdown converter`() {
        client.get().uri("/api/post/reactor-bismuth-is-out?converter=markdown").retrieve().bodyToMono<Post>()
                .test()
                .consumeNextWith {
                    assertThat(it.title).startsWith("<p>Reactor Bismuth is out")
                    assertThat(it.headline).doesNotContain("**3.1.0.RELEASE**").contains("<strong>3.1.0.RELEASE</strong>")
                    assertThat(it.content).doesNotContain("[Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)").contains("<a href=\"https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga\">")
                    assertThat(it.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
                    assertThat(it.author).isEqualTo("simonbasle")
                }.verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API with invalid converter`() {
        client.get().uri("/api/post/reactor-bismuth-is-out?converter=foo").exchange()
                .test()
                .consumeNextWith { assertThat(it.statusCode()).isEqualTo(INTERNAL_SERVER_ERROR) }
                .verifyComplete()
    }

    @Test
    fun `Verify post JSON API and notifications via SSE`() {
        client.get().uri("/api/post/notifications").accept(TEXT_EVENT_STREAM).retrieve().bodyToFlux<PostEvent>()
                .take(1)
                .doOnSubscribe {
                    client.post().uri("/api/post/").syncBody(Post("foo", "Foo", "foo", "foo", "mark", LocalDateTime.now())).exchange().subscribe()
                }
                .test()
                .consumeNextWith {
                    assertThat(it.slug).isEqualTo("foo")
                    assertThat(it.title).isEqualTo("Foo")
                }
                .verifyComplete()
    }

}
