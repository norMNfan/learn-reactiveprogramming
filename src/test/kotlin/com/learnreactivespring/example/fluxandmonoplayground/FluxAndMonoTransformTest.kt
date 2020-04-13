package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxAndMonoTransformTest {
    val names = listOf("adam", "anna", "jack", "jenny")

    @Test
    fun transformUsingMap() {
        val namesFlux = Flux.fromIterable(names)
            .map { it.toUpperCase() }
            .log()

        StepVerifier.create(namesFlux)
            .expectNext("ADAM", "ANNA", "JACK", "JENNY")
            .verifyComplete()
    }

    @Test
    fun transformUsingMap_Length() {
        val namesFlux = Flux.fromIterable(names)
            .map { it.length }
            .log()

        StepVerifier.create(namesFlux)
            .expectNext(4,4,4,5)
            .verifyComplete()
    }

    @Test
    fun transformUsingMap_Length_repeat() {
        val namesFlux = Flux.fromIterable(names)
            .map { it.length }
            .repeat(1)
            .log()

        StepVerifier.create(namesFlux)
            .expectNext(4,4,4,5,4,4,4,5)
            .verifyComplete()
    }

    @Test
    fun transformUsingMap_Filter() {
        val namesFlux = Flux.fromIterable(names)
            .filter { it.length > 4 }
            .map { it.toUpperCase() }
            .log()

        StepVerifier.create(namesFlux)
            .expectNext("JENNY")
            .verifyComplete()
    }

    @Test
    fun transformUsingFlatMap() {
        val namesFlux = Flux.fromIterable(listOf("A", "B", "C", "D", "E", "F"))
            .flatMap { Flux.fromIterable(convertToList(it)) } // db or external service call that returns a Flux
            .log()

        StepVerifier.create(namesFlux)
            .expectNextCount(12)
            .verifyComplete()
    }

    fun convertToList(s: String): List<String> {
        Thread.sleep(1000)
        return listOf(s, "newValue")
    }
}