package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import javax.management.RuntimeErrorException

class FluxAndMonoTest {
    @Test
    fun fluxTest() {
        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
            .concatWith(Flux.just("After Error"))
            .log()

        stringFlux.subscribe{
            println("$it"
        ) }
    }

    @Test
    fun fluxTestElements_WithoutError() {
        val stringFlux = Flux.just("String", "Spring Rest", "Reactive Spring")
            .concatWith(Flux.error(RuntimeException("Exception occured")))
            .log()

        StepVerifier.create(stringFlux)
            .expectNext("String")
            .expectNext("Spring Rest")
            .expectNext("Reactive Spring")
            .expectErrorMessage("Exception occured")
            .verify()
    }

    @Test
    fun fluxTestElementsCount_WithError() {
        val stringFlux = Flux.just("String", "Spring Rest", "Reactive Spring")
            .concatWith(Flux.error(RuntimeException("Exception occured")))
            .log()

        StepVerifier.create(stringFlux)
            .expectNextCount(3)
            .expectError(java.lang.RuntimeException::class.java)
    }

    @Test
    fun fluxTestElements_WithError() {
        val stringFlux = Flux.just("String", "Spring Rest", "Reactive Spring")
            .concatWith(Flux.error(RuntimeException("Exception occured")))
            .log()

        StepVerifier.create(stringFlux)
            .expectNext("String", "Spring Rest", "Reactive Spring")
            .expectError(java.lang.RuntimeException::class.java)
    }

    @Test
    fun monoTest() {
        val mono = Mono.just("Spring")

        StepVerifier.create(mono.log())
            .expectNext("Spring")
            .verifyComplete()

    }
}