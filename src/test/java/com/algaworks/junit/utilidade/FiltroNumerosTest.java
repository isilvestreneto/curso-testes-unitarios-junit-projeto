package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroNumerosTest {

    @Test
    public void deveRetornarNumerosPares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> esperado = Arrays.asList(2, 4);

        List<Integer> resultado = FiltroNumeros.numerosPares(numeros);

        assertEquals(esperado, resultado);
        assertArrayEquals(esperado.toArray(), resultado.toArray());
    }

}