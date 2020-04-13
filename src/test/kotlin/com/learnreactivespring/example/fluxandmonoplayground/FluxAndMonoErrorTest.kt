package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.lang.RuntimeException

class FluxAndMonoErrorTest {
    @Test
    fun fluxErrorHandling() {

        val stringFlux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("exception")))
            .concatWith(Flux.just("D"))
            .onErrorResume {
                println("$it")
                Flux.just("default", "default1")
            }

        StepVerifier.create(stringFlux.log())
            .expectSubscription()
            .expectNext("A", "B", "C")
            .expectNext("default", "default1")
            .verifyComplete()
    }

    @Test
    fun fluxErrorHandling_onErrorReturn() {

        val stringFlux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("exception")))
            .concatWith(Flux.just("D"))
            .onErrorReturn("default")

        StepVerifier.create(stringFlux.log())
            .expectSubscription()
            .expectNext("A", "B", "C")
            .expectNext("default")
            .verifyComplete()
    }
}