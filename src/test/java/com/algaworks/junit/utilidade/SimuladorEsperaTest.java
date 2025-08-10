package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class SimuladorEsperaTest {

    @Test
    void deveEsperarENaoDarTimeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            SimuladorEspera.esperar(Duration.ofMillis(10));
        });
    }
}