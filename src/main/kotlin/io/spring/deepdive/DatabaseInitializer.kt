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
package io.spring.deepdive


import java.time.LocalDateTime
import java.util.Arrays

import io.spring.deepdive.model.Post
import io.spring.deepdive.model.PostEvent
import io.spring.deepdive.model.User
import io.spring.deepdive.repository.PostRepository
import io.spring.deepdive.repository.UserRepository

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.MongoOperations


@Component
class DatabaseInitializer(
        private val ops: MongoOperations,
        private val userRepository: UserRepository,
        private val postRepository: PostRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        ops.createCollection(PostEvent::class.java, CollectionOptions.empty().capped().size(10000))

        val brian = User("bclozel", "Brian", "Clozel", "Spring Framework & Spring Boot @pivotal — @LaCordeeLyon coworker")
        val mark = User("MkHeck","Mark", "Heckler", "Spring Developer Advocate @Pivotal. Computer scientist+MBA, inglés y español, @Java_Champions. Pragmatic optimist. #Spring #Reactive #Microservices #IoT #Cloud")
        val arjen = User("poutsma", "Arjen", "Poutsma")
        val rossen = User("rstoyanchev", "Rossen", "Stoyanchev", "Spring Framework committer @Pivotal")
        val sam = User("sam_brannen", "Sam", "Brannen", "Core @SpringFramework & @JUnitTeam Committer. Enterprise @Java Consultant at @Swiftmind. #Spring Trainer. Spring User Group Lead at @JUGCH.")
        val seb = User("sdeleuze", "Sebastien", "Deleuze", "Spring Framework committer @Pivotal, @Kotlin addict, #WebAssembly believer, @mixitconf organizer, #techactivism")
        val simon = User("simonbasle", "Simon", "Basle", "software development aficionado, Reactor Software Engineer @pivotal")
        val stephanem = User("smaldini", "Stephane", "Maldini", "Project Reactor Lead @Pivotal -All things Reactive and Distributed - ex Londoner - opinions != Pivotal")
        val stephanen = User("snicoll", "Stephane", "Nicoll", "Proud husband. Passionate and enthusiastic Software engineer. Working on @springboot, @springframework & Spring Initializr at @Pivotal")
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        val violeta = User("violetagg", "Violeta", "Georgieva", "All views are my own!")

        userRepository.saveAll(Arrays.asList(brian, mark, arjen, rossen, sam, seb, simon, stephanem, stephanen, juergen, violeta)).blockLast()

        val reactorTitle = "Reactor Bismuth is out"
        val reactorPost = Post(
                reactorTitle.slugify(),
                reactorTitle,
                """It is my great pleasure to announce the GA release of **Reactor Bismuth**, which notably encompasses
                    |`reactor-core` **3.1.0.RELEASE** and `reactor-netty` **0.7.0.RELEASE** \uD83C\uDF89""".trimMargin(),
                """With the release of [Spring Framework 5.0](https://spring.io/blog/2017/09/28/spring-framework-5-0-goes-ga)
                    |now just happening, you can imagine this is a giant step for Project Reactor :)""".trimMargin(),
                "simonbasle",
                LocalDateTime.of(2017, 9, 28, 12, 0)
        )

        val springTitle = "Spring Framework 5.0 goes GA"
        val spring5Post = Post(
                springTitle.slugify(),
                springTitle,
                """Dear Spring community,
                    |It is my pleasure to announce that, after more than a year of milestones and RCs and almost two years of development overall,
                    |Spring Framework 5.0 is finally generally available as 5.0.0.RELEASE from [repo.spring.io](https://repo.spring.io)
                    |and Maven Central!""".trimMargin(),
                """This brand-new generation of the framework is ready for 2018 and beyond: with support for JDK 9 and the Java EE 8 API level (e.g. Servlet 4.0),
                    |as well as comprehensive integration with Reactor 3.1, JUnit 5, and the Kotlin language. On top of that all, Spring Framework 5 comes with
                    |many functional API variants and introduces a dedicated reactive web framework called Spring WebFlux, next to a revised version of our
                    |Servlet-based web framework Spring MVC.""".trimMargin(),
                "springjuergen",
                LocalDateTime.of(2017, 9, 28, 11, 30)
        )

        val postTitle = "Introducing Kotlin support in Spring Framework 5.0"
        val springKotlinPost = Post(
                postTitle.slugify(),
                postTitle,
                """Following the [Kotlin support on start.spring.io](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin)
                    |we introduced a few months ago, we have continued to work to ensure that Spring and [Kotlin](https://kotlin.link/) play well together.""".trimMargin(),
                """One of the key strengths of Kotlin is that it provides a very good [interoperability](https://kotlinlang.org/docs/reference/java-interop.html)
                    |with libraries written in Java. But there are ways to go even further and allow writing fully idiomatic Kotlin code when developing your next
                    |Spring application. In addition to Spring Framework support for Java 8 that Kotlin applications can leverage like functional web or bean registration APIs,
                    |there are additional Kotlin dedicated features that should allow you to reach a new level of productivity.""".trimMargin(),
                "sdeleuze",
                LocalDateTime.of(2017, 1, 4, 9, 0)
        )
        postRepository.saveAll(Arrays.asList(reactorPost, spring5Post, springKotlinPost)).blockLast()
    }
}
