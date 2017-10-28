package io.spring.deepdive

import com.samskivert.mustache.Mustache.*

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun mustacheCompiler(loader: TemplateLoader) =
            compiler().escapeHTML(false).withLoader(loader)

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}