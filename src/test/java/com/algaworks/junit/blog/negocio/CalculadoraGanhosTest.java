package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
    void Dado_um_post_e_autor_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_com_bonus() {
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(BigDecimal.valueOf(40), ganhos.getTotalGanho());
    }

    @Test
    void Dado_um_post_e_autor_Quando_calcular_ganhos_Entao_deve_retornar_quantidade_de_palavras() {
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(6, ganhos.getQuantidadePalavras());
    }

    @Test
    void Dado_um_post_e_um_autor_Quando_calcular_ganhos_Entao_deve_retornar_valor_pago_por_palavra_do_autor() {
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    void Dado_um_post_e_um_autor_nao_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_sem_bonus() {
        autor.setPremium(false);
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(BigDecimal.valueOf(30), ganhos.getTotalGanho());
    }

}