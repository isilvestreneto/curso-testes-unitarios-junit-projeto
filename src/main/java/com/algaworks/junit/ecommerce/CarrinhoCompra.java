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
        Objects.requireNonNull(produto, "Produto não pode ser nulo");

        // caso o produto não exista, deve retornar uma exception
        boolean temProduto = verificarSeTemProduto(produto);

        if (!temProduto) {
            throw new IllegalArgumentException("Não existe produto informado");
        }

        // deve remover o produto independente da quantidade
        itens.removeIf(iten -> iten.getProduto().equals(produto));
    }

    public void aumentarQuantidadeProduto(Produto produto) {
        // parâmetro não pode ser nulo, deve retornar uma exception
        Objects.requireNonNull(produto, "Produto não pode ser nulo");

        // caso o produto não exista, deve retornar uma exception

        boolean temProduto = verificarSeTemProduto(produto);

        if (!temProduto) {
            throw new IllegalArgumentException("Produto não cadastrado");
        }

        // deve aumentar em um quantidade do produto
        itens.iterator().forEachRemaining(item -> {
            if (item.getProduto().equals(produto)) {
                item.adicionarQuantidade(1);
            }
        });
    }

    public void diminuirQuantidadeProduto(Produto produto) {
        Objects.requireNonNull(produto);

        boolean temProduto = verificarSeTemProduto(produto);

        if (!temProduto) {
            throw new IllegalArgumentException("Produto não cadastrado");
        }

        itens.iterator().forEachRemaining(item -> {
            if (item.getProduto().equals(produto)) {
                item.subtrairQuantidade(1);
            }
        });
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(i -> i.getProduto().getValor()
                        .multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getQuantidadeTotalDeProdutos() {
        return itens.stream()
                .map(i -> i.getQuantidade())
                .reduce(0, Integer::sum);
    }

    public void esvaziar() {
        itens.clear();
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