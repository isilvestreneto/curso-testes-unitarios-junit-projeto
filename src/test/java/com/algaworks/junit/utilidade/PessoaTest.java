package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaTest {

    @Test
    void assercaoAgrupada() {
        Pessoa pessoa = new Pessoa("João", "Silva");
        assertAll("Asserções de pessoa",
                () -> assertEquals("João", pessoa.getNome(), "O nome deve ser João"),
                () -> assertEquals("Silva", pessoa.getSobrenome(), "O sobrenome deve ser Silva"),
                () -> assertEquals("JoãoSilva", pessoa.getNomeCompleto(), "O nome completo deve ser JoãoSilva")
        );
    }

}