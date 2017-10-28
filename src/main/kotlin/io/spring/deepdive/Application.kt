package io.spring.deepdive

import com.samskivert.mustache.Mustache.*

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun mustacheCompiler(loader: TemplateLoader) =
            compiler().escapeHTML(false).withLoader(loader)

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}