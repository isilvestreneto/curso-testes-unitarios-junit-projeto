package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudar() {
        String saudacao = SaudacaoUtil.saudar(9);
        System.out.println(saudacao);
        assertEquals("Bom dia", saudacao);
        assertTrue(saudacao.equals("Bom dia"));
    }
}