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

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostJsonApiTests extends AbstractIntegrationTests {

    @Test
    public void assertFindAllJsonApiIsParsedCorrectlyAndContains3Elements() {
        List<Post> posts = restTemplate.exchange("/api/post/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
        assertThat(posts).hasSize(3);
    }

    @Test
    public void verifyFindOneJsonApi() {
        Post post = restTemplate.getForObject("/api/post/reactor-bismuth-is-out", Post.class);
        assertThat(post.getTitle()).isEqualTo("Reactor Bismuth is out");
        assertThat(post.getHeadline()).startsWith("It is my great pleasure to");
        assertThat(post.getContent()).startsWith("With the release of");
        assertThat(post.getAddedAt()).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 00));
        assertThat(post.getAuthor().getFirstname()).isEqualTo("Simon");
    }

    @Test
    public void verifyFindOneJsonApiWithMarkdownConverter() {
        Post post = restTemplate.getForObject("/api/post/reactor-bismuth-is-out?converter=markdown", Post.class);
        assertThat(post.getTitle()).startsWith("Reactor Bismuth is out");
        assertThat(post.getHeadline()).doesNotContain("**3.1.0.RELEASE**").contains("<strong>3.1.0.RELEASE</strong>");
        assertThat(post.getContent()).doesNotContain("[Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)").contains("<a href=\"https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga\">");
        assertThat(post.getAddedAt()).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 00));
        assertThat(post.getAuthor().getFirstname()).isEqualTo("Simon");
    }

    @Test
    public void verifyFindOneJsonApiWithInvalidConverter() {
        assertThatThrownBy(() -> restTemplate.getForEntity("/api/post/reactor-bismuth-is-out?converter=foo", Post.class)).isInstanceOf(HttpServerErrorException.class);
    }

}
