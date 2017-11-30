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

import io.spring.deepdive.model.User

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserJsonApiTests(@LocalServerPort port: Int) {

    // TODO Migrate to WebTestClient when https://youtrack.jetbrains.com/issue/KT-5464 will be fixed
    private val client = WebClient.create("http://localhost:$port")

    @Test
    fun `Assert FindAll JSON API is parsed correctly and contains 11 elements`() {
        client.get().uri("/api/user/").retrieve().bodyToFlux<User>()
                .test()
                .expectNextCount(11)
                .verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API`() {
        client.get().uri("/api/user/MkHeck").retrieve().bodyToMono<User>()
                .test()
                .consumeNextWith {
                    assertThat(it.login).isEqualTo("MkHeck")
                    assertThat(it.firstname).isEqualTo("Mark")
                    assertThat(it.lastname).isEqualTo("Heckler")
                    assertThat(it.description).startsWith("Spring Developer Advocate")
                }.verifyComplete()
    }

}
