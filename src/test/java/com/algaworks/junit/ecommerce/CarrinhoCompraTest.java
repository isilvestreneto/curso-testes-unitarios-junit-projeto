package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CarrinhoCompraTest {

    CarrinhoCompra carrinhoCompra;
    Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Pedro");
    }

    @Nested
    @DisplayName("Get Itens")
    class DeveRetornarUmaNovaListaDeItens {
        @Test
        @DisplayName("Deve retornar uma nova lista de itens")
        void some() {
            carrinhoCompra = new CarrinhoCompra(cliente);
            assertEquals(Collections.emptyList(), carrinhoCompra.getItens());
        }
    }

    @Nested
    @DisplayName("Cenarios Adicionar Produto")
    class AdicionarProduto {
        @Test
        @DisplayName("Deve adicionar produto")
        void adicionarProduto() {
            carrinhoCompra = new CarrinhoCompra(cliente);
            Produto produto = new Produto(1L, "Produto 1", "Produto um", BigDecimal.TEN);
            carrinhoCompra.adicionarProduto(produto, 1);
            assertEquals(1, carrinhoCompra.getItens().size());
        }


        @Test
        @DisplayName("Deve lancar exception quando qualquer um dos parametros forem nulos")
        void deveLancarExceptionQuandoParametrosForemNulos() {
            carrinhoCompra = new CarrinhoCompra(cliente);
            Produto produto = new Produto(1L, "Produto 1", "Produto um", BigDecimal.TEN);
            assertAll(() -> assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(null, 1)), () -> assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(produto, null)), () -> assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(produto, -1)));
        }

        @Test
        @DisplayName("Deve incrementar a quantidade caso produto já exista")
        void deveIncrementarQuantidadeCasoProdutoJaExista() {

            Produto produto = new Produto(1L, "Boneca", "Um brinquedo infantil", BigDecimal.ONE);
            ArrayList<ItemCarrinhoCompra> itens = new ArrayList<>();
            ItemCarrinhoCompra itemCarrinhoCompra = new ItemCarrinhoCompra(produto, 1);
            itens.add(itemCarrinhoCompra);
            carrinhoCompra = new CarrinhoCompra(cliente, itens);

            carrinhoCompra.adicionarProduto(produto, 1);

            carrinhoCompra.getItens().stream().forEach((item) -> {
                int quantidade = item.getQuantidade();
                assertEquals(quantidade, itemCarrinhoCompra.getQuantidade());
            });
        }
    }

    @Nested
    @DisplayName("Cenarios Remover Produto")
    class RemoverProduto {
        Produto produto;
        ArrayList<ItemCarrinhoCompra> itens;
        ItemCarrinhoCompra itemCarrinhoCompra;
        @BeforeEach
        void setUp() {
            produto = new Produto(1L, "Boneca", "Um brinquedo infantil", BigDecimal.ONE);
            itens = new ArrayList<>();
            itemCarrinhoCompra = new ItemCarrinhoCompra(produto, 1);
            itens.add(itemCarrinhoCompra);
            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Test
        @DisplayName("Deve lançar exceção quando produto informado é nulo")
        void deveLancarExcecaoQuandoProdutoInformadoNulo() {
            assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.removerProduto(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando produto informado não existe no carrinho")
        void deveLancarExcecaoQUandoProdutoInformadoNaoExisteNoCarrinho() {
            Produto produtoNaoExistente = new Produto(2L, "Carrinho", "Um brinquedo infantil", BigDecimal.ONE);
            assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.removerProduto(produtoNaoExistente));
        }

        @Test
        @DisplayName("Deve remover produto do carrinho")
        void deveRemoverProdutoDoCarrinho() {
            carrinhoCompra.removerProduto(produto);
            assertEquals(0, carrinhoCompra.getItens().size());
        }
    }
}