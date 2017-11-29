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

import io.spring.deepdive.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleJsonApiTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void assertFindAllJsonApiIsParsedCorrectlyAndContains3Elements() {
        List<Article> articles = restTemplate.exchange("/api/article/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>() {}).getBody();
        assertThat(articles).hasSize(3);
    }

    @Test
    public void verifyFindOneJsonApi() {
        Article article = restTemplate.getForObject("/api/article/reactor-bismuth-is-out", Article.class);
        assertThat(article.getTitle()).isEqualTo("Reactor Bismuth is out");
        assertThat(article.getHeadline()).startsWith("It is my great pleasure to");
        assertThat(article.getContent()).startsWith("With the release of");
        assertThat(article.getAddedAt()).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 00));
        assertThat(article.getAuthor().getFirstname()).isEqualTo("Simon");
    }

    @Test
    public void verifyFindOneJsonApiWithMarkdownConverter() {
        Article article = restTemplate.getForObject("/api/article/reactor-bismuth-is-out?converter=markdown", Article.class);
        assertThat(article.getTitle()).startsWith("Reactor Bismuth is out");
        assertThat(article.getHeadline()).doesNotContain("**3.1.0.RELEASE**").contains("<strong>3.1.0.RELEASE</strong>");
        assertThat(article.getContent()).doesNotContain("[Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)").contains("<a href=\"https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga\">");
        assertThat(article.getAddedAt()).isEqualTo(LocalDateTime.of(2017, 9, 28, 12, 00));
        assertThat(article.getAuthor().getFirstname()).isEqualTo("Simon");
    }

    @Test
    public void verifyFindOneJsonApiWithInvalidConverter() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/article/reactor-bismuth-is-out?converter=foo", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).contains("Only markdown converter is supported");
    }

}
