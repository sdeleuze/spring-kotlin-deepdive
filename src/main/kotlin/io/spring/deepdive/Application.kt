package io.spring.deepdive

import com.samskivert.mustache.Mustache.*

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableCoroutineMongoRepositories
import org.springframework.kotlin.experimental.coroutine.EnableCoroutine

@SpringBootApplication
@EnableCoroutine
@EnableCoroutineMongoRepositories
class Application {

    @Bean
    fun mustacheCompiler(loader: TemplateLoader) =
            compiler().escapeHTML(false).withLoader(loader)

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}