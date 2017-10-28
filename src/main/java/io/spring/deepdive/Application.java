package io.spring.deepdive;

import com.samskivert.mustache.Mustache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.samskivert.mustache.Mustache.*;

@SpringBootApplication
public class Application {

    @Bean
    public Mustache.Compiler mustacheCompiler(TemplateLoader loader) {
        return compiler().escapeHTML(false).withLoader(loader);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
