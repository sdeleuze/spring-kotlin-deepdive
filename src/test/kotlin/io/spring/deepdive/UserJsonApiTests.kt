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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.getForObject

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserJsonApiTests(@LocalServerPort port: Int, @Autowired builder: RestTemplateBuilder) {

    // We don't use TestRestTemplate because of Spring Boot issues #10761 and #8062
    private val restTemplate = builder.rootUri("http://localhost:$port").build()

    @Test
    fun `Assert FindAll JSON API is parsed correctly and contains 11 elements`() {
        val users = restTemplate.getForObject<List<User>>("/api/user/")
        assertThat(users).hasSize(11)
    }

    @Test
    fun `Verify findOne JSON API`() {
        val user = restTemplate.getForObject<User>("/api/user/MkHeck")!!
        assertThat(user.login).isEqualTo("MkHeck")
        assertThat(user.firstname).isEqualTo("Mark")
        assertThat(user.lastname).isEqualTo("Heckler")
        assertThat(user.description).startsWith("Spring Developer Advocate")
    }

}
