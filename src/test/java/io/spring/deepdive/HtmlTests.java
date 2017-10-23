package io.spring.deepdive;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlTests extends AbstractIntegrationTests {

    @Test
    public void assertContentOnBlogPage() {
        String body = restTemplate.getForObject("/", String.class);
        assertThat(body)
                .contains("Reactor Bismuth is out")
                .contains("September 28th")
                .contains("Sebastien")
                .doesNotContain("brand-new generation");
    }

    @Test
    public void assertContentOnBlogPostPage() {
        String body = restTemplate.getForObject("/spring-framework-5-0-goes-ga", String.class);
        assertThat(body)
                .contains("Spring Framework 5.0 goes GA")
                .contains("Dear Spring community")
                .contains("brand-new generation")
                .contains("Juergen")
                .doesNotContain("Sebastien");
    }

}
