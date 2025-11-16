package com.projeto_oo.app;

import com.projeto_oo.domain.ContaBancaria;
import com.projeto_oo.domain.ContaCorrente;
import com.projeto_oo.domain.ContaPoupanca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorBancario {
    
    public static void main(String[] args) {
        ContaCorrente ccPadrao = new ContaCorrente();
        ContaPoupanca cpPadrao = new ContaPoupanca();

        ccPadrao.setNumero("1234-5");
        ccPadrao.setTitular("João da Silva");
        ccPadrao.setSaldo(new BigDecimal("1500.00"));

        cpPadrao.setNumero("9999-1");
        cpPadrao.setTitular("Maria Oliveira");
        cpPadrao.setSaldo(new BigDecimal("2000.00"));

        ContaCorrente ccCompleta = new ContaCorrente(
                "5555-0", "Carla Nunes",
                new BigDecimal("500.00"), true,
                new BigDecimal("1000.00"), new BigDecimal("24.90")
        );

        ContaPoupanca cpCompleta = new ContaPoupanca(
                "8888-8", "Paulo Santos",
                new BigDecimal("3000.00"), true,
                new BigDecimal("0.0075"), 10
        );

        List<ContaBancaria> contas = new ArrayList<>();
        contas.add(ccPadrao);
        contas.add(cpPadrao);
        contas.add(ccCompleta);
        contas.add(cpCompleta);

        for (ContaBancaria c : contas) {
            System.out.println();
            c.exibirInformacoes();
            System.out.println("Saldo projetado (sem params): R$ " + c.calcularSaldoProjetado());
        }
    }
}
