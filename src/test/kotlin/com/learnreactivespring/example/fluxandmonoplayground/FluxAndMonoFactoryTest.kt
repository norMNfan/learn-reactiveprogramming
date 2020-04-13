package com.learnreactivespring.example.fluxandmonoplayground

import com.sun.jna.StringArray
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
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
    }
}