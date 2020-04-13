package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class FluxAndMonoFactoryTest {

    val names = listOf("adam", "anna", "jack", "jenny")

    @Test
    fun fluxUsingIterable() {
        val namesFlux = Flux.fromIterable(names)
            .log()

        StepVerifier.create(namesFlux)
            .expectNext("adam", "anna", "jack", "jenny")
            .verifyComplete()
    }

    @Test
    fun fluxUsingArray() {
        val names = arrayOf("adam", "anna", "jack", "jenny")

        val namesFlux = Flux.fromArray(names).log()

        StepVerifier.create(namesFlux)
            .expectNext("adam", "anna", "jack", "jenny")
            .verifyComplete()
    }

    @Test
    fun monoUsingJustOrEmpty() {
       val mono: Mono<String> = Mono.justOrEmpty(null)

        StepVerifier.create(mono.log())
            .verifyComplete()
    }

    @Test
    fun monoUsingSupplier() {
        val stringSupplier = { "adam" }

        val stringMono = Mono.fromSupplier(stringSupplier)

        StepVerifier.create(stringMono.log())
            .expectNext("adam")
            .verifyComplete()
    }

    @Test
    fun fluxUsingRange() {
        val integerFlux = Flux.range(1,5)

        StepVerifier.create(integerFlux)
            .expectNext(1,2,3,4,5)
            .verifyComplete()
    }
}