package io.spring.deepdive;

import com.samskivert.mustache.Mustache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader templateLoader) {
        return Mustache.compiler().escapeHTML(false).withLoader(templateLoader);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
