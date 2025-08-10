package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaTest {

    //    @Disabled("Desabilitado para demonstração")
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    void assercaoAgrupada() {
        Assumptions.assumeTrue("PROD".equals(System.getenv("PROD")), () -> "Abortando teste: Não deve ser executado em PROD");
        Pessoa pessoa = new Pessoa("João", "Silva");
        assertAll("Asserções de pessoa",
                () -> assertEquals("João", pessoa.getNome(), "O nome deve ser João"),
                () -> assertEquals("Silva", pessoa.getSobrenome(), "O sobrenome deve ser Silva"),
                () -> assertEquals("JoãoSilva", pessoa.getNomeCompleto(), "O nome completo deve ser JoãoSilva")
        );
    }

}