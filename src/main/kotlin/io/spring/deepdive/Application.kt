package io.spring.deepdive

import com.mongodb.connection.netty.NettyStreamFactoryFactory
import com.samskivert.mustache.Mustache.*

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun mustacheCompiler(loader: TemplateLoader) =
            compiler().escapeHTML(false).withLoader(loader)

    @Bean  // Remove with Boot 2 RC1, see gh-10961
    fun clientSettingsBuilderCustomizer() =
            MongoClientSettingsBuilderCustomizer { it.streamFactoryFactory(NettyStreamFactoryFactory.builder().build()) }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}