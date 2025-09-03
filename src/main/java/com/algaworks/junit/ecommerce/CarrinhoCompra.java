package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarrinhoCompra {

    private final Cliente cliente;
    private final List<ItemCarrinhoCompra> itens;

    public CarrinhoCompra(Cliente cliente) {
        this(cliente, new ArrayList<>());
    }

    public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
        Objects.requireNonNull(cliente);
        Objects.requireNonNull(itens);
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
    }

    public List<ItemCarrinhoCompra> getItens() {
        return itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void adicionarProduto(Produto produto, Integer quantidade) {
        // parâmetros não podem ser nulos, deve retornar uma exception
        if (produto == null || quantidade == null) {
            throw new IllegalArgumentException("Deve passar parâmetros válidos");
        }

        // quantidade não pode ser menor que 1
        if (quantidade < 1) {
            throw new IllegalArgumentException("Quantidade deve ser maior que 1");
        }

        boolean temProduto = verificarSeTemProduto(produto);

        // deve incrementar a quantidade caso o produto já exista
        if (temProduto) {
            for (ItemCarrinhoCompra iten : itens) {
                iten.adicionarQuantidade(quantidade);
            }
        } else {
            itens.add(new ItemCarrinhoCompra(produto, quantidade));
        }
    }

    private boolean verificarSeTemProduto(Produto produto) {
        for (ItemCarrinhoCompra iten : itens) {
            if (iten.getProduto().equals(produto)) {
                return true;
            }
        }
        return false;
    }

    public void removerProduto(Produto produto) {
        // parâmetro não pode ser nulo, deve retornar uma exception
        if (produto == null) {
            throw new IllegalArgumentException("Deve inserir o produto que quer remover");
        }

        // caso o produto não exista, deve retornar uma exception
        boolean temProduto = verificarSeTemProduto(produto);

        if (!temProduto) {
            throw new IllegalArgumentException("Não existe produto informado");
        }

        // deve remover o produto independente da quantidade
        itens.removeIf(iten -> iten.getProduto().equals(produto));
    }

    public void aumentarQuantidadeProduto(Produto produto) {
        //TODO parâmetro não pode ser nulo, deve retornar uma exception
        //TODO caso o produto não exista, deve retornar uma exception
        //TODO deve aumentar em um quantidade do produto
    }

    public void diminuirQuantidadeProduto(Produto produto) {
        //TODO parâmetro não pode ser nulo, deve retornar uma exception
        //TODO caso o produto não exista, deve retornar uma exception
        //TODO deve diminuir em um quantidade do produto, caso tenha apenas um produto, deve remover da lista
    }

    public BigDecimal getValorTotal() {
        //TODO implementar soma do valor total de todos itens
        return null;
    }

    public int getQuantidadeTotalDeProdutos() {
        //TODO retorna quantidade total de itens no carrinho
        //TODO Exemplo em um carrinho com 2 itens, com a quantidade 2 e 3 para cada item respectivamente, deve retornar 5
        return 0;
    }

    public void esvaziar() {
        //TODO deve remover todos os itens
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrinhoCompra that = (CarrinhoCompra) o;
        return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itens, cliente);
    }
}