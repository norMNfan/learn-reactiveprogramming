package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxAndMonoFilterTest {
    val names = listOf("adam", "anna", "jack", "jenny")

    @Test
    fun filterTest() {
        val namesFlux = Flux.fromIterable(names)
            .filter{ it.startsWith("a") }
            .log()

        StepVerifier.create(namesFlux)
            .expectNext("adam", "anna")
            .verifyComplete()
    }
}