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

import java.time.LocalDateTime;
import java.util.List;

import io.spring.deepdive.model.Post;
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
public class PostApiTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void assertFindAllJsonApiIsParsedCorrectlyAndContains3Elements() {
        List<Post> posts = this.restTemplate.exchange("/api/post/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
        assertThat(posts).hasSize(3);
    }

    @Test
    public void verifyFindOneJsonApi() {
        Post post = this.restTemplate.getForObject("/api/post/reactor-bismuth-is-out", Post.class);
        assertThat(post.getTitle()).isEqualTo("Reactor Bismuth is out");
        assertThat(post.getHeadline()).startsWith("It is my great pleasure to");
        assertThat(post.getContent()).startsWith("With the release of");
        assertThat(post.getAddedAt()).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 00));
        assertThat(post.getAuthor().getFirstname()).isEqualTo("Simon");
    }

}
