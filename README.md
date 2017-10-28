# Spring Kotlin deepdive

This project is designed to show step by step how to migrate from Java to Kotlin with
Spring Boot step by step:
 * [Step 0](https://github.com/sdeleuze/spring-kotlin-deepdive/): Initial Java project
 * [Step 1](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step1): Java to Kotlin
 * [Step 2](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step2): Spring Boot 2
 * [Step 3](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step3): Spring WebFlux
 * [Step 4](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step4): Kotlin routing DSL
 
See [Spring Kotlin support documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html#kotlin) for more details.

## [Step 1](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step1): Java to Kotlin

Code:
* No more semicolon at end of lines
* Type suffixed with colon, as statically typed as Java, optional type inference
* Show how to configure return type inference hints
* Short syntax for declaring properties and initializing them from the primary constructor instead of super verbose mostly auto-generated POJO
* [Data classes](https://kotlinlang.org/docs/reference/data-classes.html)
* [Data classes](https://kotlinlang.org/docs/reference/data-classes.html)
* Syntax help using naturally immutable classes
* `:` instead of `extends`
* No need for `{ }` for empty classes / interfaces
* Optional parameter default value
* `public` by the default
* `fun` to declare functions
* Better lambdas: `{ }` last parameter notation, lambda without collect, `it` default parameter
* Constructor without `new`
* `main()` top level method
* `Utils` class -> [Kotlin extensions](https://kotlinlang.org/docs/reference/extensions.html) 
* `.getBody()` -> `.body`
* Meaningful function names between backticks
* Show copy and paste Java to Kotlin

Build:
* Dependencies:
	* `kotlin-stdlib-jre8`
	* `kotlin-reflect`
	* `jackson-module-kotlin`
* Plugins:
	* `kotlin`
	* `kotlin-spring`
	* `kotlin-noarg`
* Configure to build Java 8 bytecode

**[Go to step 2: Spring Boot 2](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step2)**
