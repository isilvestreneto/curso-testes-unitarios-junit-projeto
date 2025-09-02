package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta bancária")
public class ContaBancariaBDDTest {

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$ 10,00")
    class ContaBancariaComSaldo {
        private ContaBancaria conta;

        @BeforeEach
        void setup() {
            conta = new ContaBancaria(BigDecimal.TEN);
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor menor")
        class SaqueValorMenor {
            private BigDecimal valorSaque = new BigDecimal("9.0");

            @Test
            @DisplayName("Então não deve lançar exceção")
            void deveLancarSaqueSemException() {
                assertDoesNotThrow(() -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("E deve subtrair do saldo")
            void deveSubtrairDoSaldo() {
                conta.saque(valorSaque);
                assertEquals(new BigDecimal("1.0"), conta.saldo());
            }
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {
            private BigDecimal valorSaque = new BigDecimal("20.0");

            @Test
            @DisplayName("Então deve lançar exceção")
            void deveLancarSaqueComException() {
                assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("E não deve alterar saldo")
            void naoDeveAlterarSaldo() {
                try {
                    conta.saque(valorSaque);
                } catch (RuntimeException e) { }
                assertEquals(BigDecimal.TEN, conta.saldo());
            }
        }

    }

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$ 0,00")
    class ContaBancariaSemSaldo {
        private ContaBancaria conta= new ContaBancaria(BigDecimal.ZERO);

        @Test
        @DisplayName("Deve lançar exceção ao tentar sacar")
        void deveLancarSaqueComException() {
            assertThrows(RuntimeException.class, () -> conta.saque(BigDecimal.TEN));
        }
    }

    @Nested
    @DisplayName("Quando efetuar um depósito de R$ 8,00")
    class DepositoComDezReais {
        private ContaBancaria conta = new ContaBancaria(new BigDecimal("8.0"));

        @Test
        @DisplayName("Então deve adicionar ao saldo")
        void deveAdicionarAoSaldo() {
            conta.deposito(BigDecimal.TEN);
            assertEquals(new BigDecimal("18.0"), conta.saldo());
        }
    }
}
