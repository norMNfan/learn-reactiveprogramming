package com.learnreactivespring.example.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class FluxAndMonoWithTimeTest {

    @Test
    fun infiniteSequence() {

        val infiniteFlux = Flux.interval(Duration.ofMillis(100)) // starts from 0 --> ...N
            .log()

        infiniteFlux.subscribe { println("$it") }

        Thread.sleep(3000)
    }

    @Test
    fun infiniteSequenceTest() {

        val finiteFlux = Flux.interval(Duration.ofMillis(100)) // starts from 0 --> ...N
            .map { it.toInt() }
            .take(3)
            .log()

        StepVerifier.create(finiteFlux)
            .expectSubscription()
            .expectNext(0, 1, 2)
            .verifyComplete()
    }

    @Test
    fun infiniteSequenceTest_withDelay() {

        val finiteFlux = Flux.interval(Duration.ofMillis(100)) // starts from 0 --> ...N
            .delayElements(Duration.ofSeconds(1))
            .map { it.toInt() }
            .take(3)
            .log()

        StepVerifier.create(finiteFlux)
            .expectSubscription()
            .expectNext(0, 1, 2)
            .verifyComplete()
    }
}