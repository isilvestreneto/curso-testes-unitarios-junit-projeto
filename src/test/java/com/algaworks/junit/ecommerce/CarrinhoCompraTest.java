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
    Produto produto;
    ArrayList<ItemCarrinhoCompra> itens;
    ItemCarrinhoCompra itemCarrinhoCompra;

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
            assertThrows(NullPointerException.class, () -> carrinhoCompra.removerProduto(null));
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

    @Nested
    @DisplayName("Cenarios Aumentar Quantidade Produto")
    class AumentarQuantidadeProduto {

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
        @DisplayName("parâmetro não pode ser nulo, deve retornar uma exception")
        void quandoParametroForNuloDeveRetornarUmaException() {
            assertThrows(NullPointerException.class, () -> carrinhoCompra.aumentarQuantidadeProduto((Produto) null));
        }

        @Test
        @DisplayName("caso o produto não exista, deve retornar uma exception")
        void casoProdutoNaoExistaDeveRetornarException() {
            Produto produtoNaoExistente = new Produto(2L, "Carrinho", "Um brinquedo infantil", BigDecimal.ONE);
            assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.aumentarQuantidadeProduto(produtoNaoExistente));
        }

        @Test
        @DisplayName("deve aumentar em um quantidade do produto")
        void deveAumentarAQuantidadeDoProduto() {
            carrinhoCompra.aumentarQuantidadeProduto(produto);
            assertEquals(2, itemCarrinhoCompra.getQuantidade());
        }
    }

    @Nested
    @DisplayName("Cenarios Diminuir Quantidade Produto")
    class DiminuirQuantidadeProduto {

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
        @DisplayName("parâmetro não pode ser nulo, deve retornar uma exception")
        void quandoParametroForNuloDeveLancarException() {
            assertThrows(NullPointerException.class, () -> carrinhoCompra.diminuirQuantidadeProduto(null));
        }

        @Test
        @DisplayName("caso o produto não exista, deve retornar uma exception")
        void quandoProdutoNaoExistirNaBaseDeveRetornarException() {
            Produto produtoNaoExistente = new Produto(2L, "Carrinho", "Um brinquedo infantil", BigDecimal.ONE);
            assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.diminuirQuantidadeProduto(produtoNaoExistente));
        }

        @Test
        @DisplayName("caso o produto não exista, deve retornar uma exception")
        void deveDeduzirItem() {
            carrinhoCompra.diminuirQuantidadeProduto(produto);
            assertEquals(0, itemCarrinhoCompra.getQuantidade());
        }
    }

    @Nested
    @DisplayName("Get Valor Total")
    class GetValorTotal {
        ArrayList<ItemCarrinhoCompra> itens;
        ItemCarrinhoCompra itemCarrinhoCompra;

        @BeforeEach
        void setUp() {
            produto = new Produto(1L, "Boneca", "Um brinquedo infantil", BigDecimal.ONE);
            itens = new ArrayList<>();
            itemCarrinhoCompra = new ItemCarrinhoCompra(produto, 10);
            itens.add(itemCarrinhoCompra);
            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Test
        @DisplayName("Deve retornar o valor total das compras")
        void deveRetornarValorTotalDasCompras() {
            assertEquals(BigDecimal.TEN, carrinhoCompra.getValorTotal());
        }
    }

    @Nested
    @DisplayName("Get Qtd Total")
    class GetQtdTotal {
        ArrayList<ItemCarrinhoCompra> itens;
        ItemCarrinhoCompra itemCarrinhoCompra;

        @BeforeEach
        void setUp() {
            produto = new Produto(1L, "Boneca", "Um brinquedo infantil", BigDecimal.ONE);
            itens = new ArrayList<>();
            itemCarrinhoCompra = new ItemCarrinhoCompra(produto, 10);
            itens.add(itemCarrinhoCompra);
            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Test
        @DisplayName("Deve retornar a qtde total de itens da compra")
        void deveRetornarValorTotalDasCompras() {
            assertEquals(10, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }
    }

    @Nested
    @DisplayName("Esvaziar")
    class Esvaziar {
        ArrayList<ItemCarrinhoCompra> itens;
        ItemCarrinhoCompra itemCarrinhoCompra;

        @BeforeEach
        void setUp() {
            produto = new Produto(1L, "Boneca", "Um brinquedo infantil", BigDecimal.ONE);
            itens = new ArrayList<>();
            itemCarrinhoCompra = new ItemCarrinhoCompra(produto, 10);
            itens.add(itemCarrinhoCompra);
            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Test
        @DisplayName("Deve esvaziar")
        void deveEsvaziar() {
            carrinhoCompra.esvaziar();
            assertEquals(0, carrinhoCompra.getItens().size());
        }
    }
}