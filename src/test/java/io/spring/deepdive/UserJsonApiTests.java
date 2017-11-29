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
package io.spring.deepdive;

import java.util.List;

import io.spring.deepdive.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserJsonApiTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void assertFindAllJsonApiIsParsedCorrectlyAndContains11Elements() {
        List<User> users = restTemplate.exchange("/api/user/", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {}).getBody();
        assertThat(users).hasSize(11);
    }

    @Test
    public void verifyFindOneJsonApi() {
        User user = restTemplate.getForObject("/api/user/MkHeck", User.class);
        assertThat(user.getLogin()).isEqualTo("MkHeck");
        assertThat(user.getFirstname()).isEqualTo("Mark");
        assertThat(user.getLastname()).isEqualTo("Heckler");
        assertThat(user.getDescription()).startsWith("Spring Developer Advocate");
    }

}
