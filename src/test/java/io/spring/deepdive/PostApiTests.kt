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
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostApiTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    @Test
    fun `assert findAll JSON API is parsed correctly and contains 3 elements`() {
        val posts = restTemplate.exchange("/api/post/", HttpMethod.GET, null, object: ParameterizedTypeReference<List<Post>>() {}).body
        assertThat(posts).hasSize(3);
    }

    @Test
    fun `verify findOne JSON API`() {
        val post = restTemplate.getForObject("/api/post/reactor-bismuth-is-out", Post::class.java)
        assertThat(post.title).isEqualTo("Reactor Bismuth is out")
        assertThat(post.headline).startsWith("It is my great pleasure to")
        assertThat(post.content).startsWith("With the release of")
        assertThat(post.addedAt).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 0))
        assertThat(post.author.firstname).isEqualTo("Simon")
    }

}
