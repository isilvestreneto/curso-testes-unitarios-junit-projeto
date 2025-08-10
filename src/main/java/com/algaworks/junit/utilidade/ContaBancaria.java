package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

public class ContaBancaria {

    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
        if (saldo == null) {
            throw new IllegalArgumentException("Saldo não pode ser nulo");
        }

        this.saldo = saldo;
    }


    public BigDecimal saldo() {
        return saldo;
    }

    public void saque(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        if (valor.compareTo(saldo()) > 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        this.saldo = saldo().subtract(valor);
    }

    public void deposito(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        this.saldo = saldo().add(valor);
    }

}
