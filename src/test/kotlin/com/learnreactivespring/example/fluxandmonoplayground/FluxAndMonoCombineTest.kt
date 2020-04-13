package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration
import java.util.function.BiFunction

class FluxAndMonoCombineTest {
    @Test
    fun combineUsingMerge() {
        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("E", "F", "G")

        val mergedFlux = Flux.merge(flux1, flux2)

        StepVerifier.create(mergedFlux)
            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun combineUsingMerge_withDelay() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("E", "F", "G").delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.merge(flux1, flux2)

        StepVerifier.create(mergedFlux.log())
            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun combineUsingConcat_withDelay() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("E", "F", "G").delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.concat(flux1, flux2)

        StepVerifier.create(mergedFlux.log())
            .expectNext("A", "B", "C", "E", "F", "G")
            .verifyComplete()
    }

    @Test
    fun combineUsingZip() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("E", "F", "G").delayElements(Duration.ofSeconds(1))
        val comb = BiFunction<String, String, String> { t1, t2 -> t1.plus(t2) }

        val mergedFlux = Flux.zip(flux1, flux2, comb)

        StepVerifier.create(mergedFlux.log())
            .expectNext("AE", "BF", "CG")
            .verifyComplete()
    }
}