package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaBancariaTest {

    @Test
    void contaBancariaSaldoNuloDeveLancarIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new ContaBancaria(null);
        });
        assertEquals("Saldo não pode ser nulo", illegalArgumentException.getMessage());
    }


    @ParameterizedTest
    @CsvSource({
            "0",
            "-100"
    })
    void deveLancarIllegalArgumentExceptionSeValorDeSaqueMenorIgualAZero(BigDecimal valor) {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.ZERO);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(valor);
        });
        assertEquals("Valor não pode ser nulo", illegalArgumentException.getMessage());

    }

    @Test
    void deveLancarIllegalArgumentExceptionSeValorDeSaqueForNulo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.ZERO);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(null);
        });
        assertEquals("Valor não pode ser nulo", illegalArgumentException.getMessage());
    }

    @Test
    void saqueComSaldoInsuficienteDeveLancarRuntimeException() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.ZERO);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            contaBancaria.saque(BigDecimal.valueOf(10));
        });
        assertEquals("Saldo insuficiente", runtimeException.getMessage());
    }

    @Test
    void saqueComSucesso() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));
        contaBancaria.saque(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), contaBancaria.getSaldo());
    }

    @Test
    void depositoComValorNuloDeveLancarExcecao() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.deposito(null);
        });
        assertEquals("Valor não pode ser nulo", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-100"
    })
    void depositoComValorMenorIgualAZeroDeveLancarExcecao(BigDecimal valor) {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.deposito(valor);
        });
        assertEquals("Valor não pode ser nulo", illegalArgumentException.getMessage());
    }

    @Test
    void depositoComSucesso() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(50));

        contaBancaria.deposito(BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(100), contaBancaria.saldo());
    }
}