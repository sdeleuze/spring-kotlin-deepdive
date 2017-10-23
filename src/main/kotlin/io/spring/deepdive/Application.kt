package io.spring.deepdive;

import com.samskivert.mustache.Mustache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
class Application {

    @Bean
    fun mustacheCompiler(templateLoader: Mustache.TemplateLoader) =
            Mustache.compiler().escapeHTML(false).withLoader(templateLoader);

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}