package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @ParameterizedTest
    @CsvSource({
            "0, Bom dia",
            "12, Boa tarde",
            "18, Boa noite"
    })
    public void saudar(int hora, String esperado) {
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals(esperado, saudacao);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 24})
    public void deveLancarException(int hora) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            SaudacaoUtil.saudar(hora);
        });
        assertEquals("Hora inválida", illegalArgumentException.getMessage());
    }

    @Test
    public void naoDeveLancarException() {
        assertDoesNotThrow(() -> {
            SaudacaoUtil.saudar(10);
        });
    }
}