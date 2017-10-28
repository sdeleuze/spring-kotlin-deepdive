# Spring Kotlin deepdive

This project is designed to show step by step how to migrate from Java to Kotlin with
Spring Boot step by step:
 * [Step 0](https://github.com/sdeleuze/spring-kotlin-deepdive/): Initial Java project
 * [Step 1](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step1): Java to Kotlin
 * [Step 2](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step2): Spring Boot 2
 * [Step 3](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step3): Spring WebFlux
 * [Step 4](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step4): Kotlin routing DSL
 
See [Spring Kotlin support documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html#kotlin) for more details.
 
## Step 2: Spring Boot 2

* Spring Data Kay
	* No need for `kotlin-noarg` plugin since it supports natively Kotlin immutable classes
* Spring Boot 2
	* `jackson-module-kotlin` and `jackson-datatype-jsr310` provided by default with Jackson starter
	* Mustache suffix is already `.mustache` by default
	* `runApplication<FooApplication>(*args)` instead of `SpringApplication.run(Application::class.java, *args)`
* Null safety
* `@RequestParam` on nullable parameter
* Extensions
* JUnit 5 + `@BeforeAll`/ `@AfterAll`

**[Go to step 3: Spring WebFlux](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step3)**
