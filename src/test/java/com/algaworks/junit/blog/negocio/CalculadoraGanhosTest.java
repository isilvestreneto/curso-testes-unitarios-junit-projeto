package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraGanhosTest {

    CalculadoraGanhos calculadora;
    Post post;
    Editor autor;

    @BeforeAll
    static void initAll() {
        System.out.println("Inicializando testes de CalculadoraGanhos");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Todos os testes de CalculadoraGanhos foram concluídos");
    }

    @BeforeEach
    void init() {
        System.out.println("Iniciando um novo teste de CalculadoraGanhos");
        calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
        autor = new Editor(1L, "Fulano", "fulano@email.com", BigDecimal.valueOf(5),
                true);
        post = new Post(1L, "Título do Post", "Conteúdo do post com várias palavras.", autor,
                "novo-post", null, false, false);
    }

    @AfterEach
    void afterEach() {
        System.out.println("Teste unitario concluído");
    }

    @Test
    void deveCalcularGanhosComPremium() {
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(BigDecimal.valueOf(40), ganhos.getTotalGanho());
        assertEquals(6, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    void deveCalcularGanhosSemPremium() {
        autor.setPremium(false);
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(BigDecimal.valueOf(30), ganhos.getTotalGanho());
        assertEquals(6, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

}