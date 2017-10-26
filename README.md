# Spring Kotlin deepdive

This project is designed to show step by step how to migrate from Java to Kotlin with
Spring Boot step by step:
 * [Step 0](https://github.com/sdeleuze/spring-kotlin-deepdive/): Initial Java project
 * [Step 1](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step1): Java to Kotlin
 * [Step 2](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step2): Spring Boot 2
 * [Step 3](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step3): Spring WebFlux
 * [Step 4](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step4): Kotlin routing DSL
 
See [Spring Kotlin support documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html#kotlin) for more details.

## [Step 0]((https://github.com/sdeleuze/spring-kotlin-deepdive)): The initial Java project

* Simple blog with JSON HTTP API
* Integration tests can be run via `./gradlew test` (or in the IDE)
* Run the project via `./gradlew bootRun` (or in the IDE) and go to `http://localhost:8080/` with your browser
* Present the Java application software design
* Reminders:
	* No need for annotating constructor when single constructor for autowiring it (as of Spring 4.3), show 2 syntaxes
	* `@RequestMapping` aliases: `@GetMapping`, `@PostMapping`, etc.
* Reload via CTRL + F9 in IDEA (CMD + SHIFT + F9 on Mac)

**[Go to step 1: Java to Kotlin](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step1)**
