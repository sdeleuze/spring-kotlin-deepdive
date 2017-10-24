# Spring Kotlin deepdive

This project is designed to show step by step how to migrate from Java to Kotlin with
Spring Boot.

## Step 2: Spring Boot 2

* Spring Data Kay
	* No need for `kotlin-noarg` plugin since it supports natively Kotlin immutable classes
* Spring Boot 2
	* `jackson-module-kotlin` and `jackson-datatype-jsr310` provided by default with Jackson starter
	* Mustache suffix is already `.mustache` by default
* Null safety
* `@RequestParam` on nullable parameter
* Extensions
* JUnit 5 + `@BeforeAll`/ `@AfterAll`

**[Go to step 3: WebFlux](https://github.com/sdeleuze/spring-kotlin-deepdive/tree/step3)**

## Step 3: WebFlux (TODO)

* Add Server-Sent Events example

## Step 4: WebFlux functional (TODO)

* Kotlin router DSL
* Usage of `ReactiveFluentMongoOperations`

## Step 5: Frontend dev with Kotlin + sharing common code (TODO)

* Inspired from https://github.com/sdeleuze/spring-kotlin-fullstack
