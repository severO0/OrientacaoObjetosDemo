package com.projeto_oo.domain;

import java.math.BigDecimal;

public class ContaPoupanca extends ContaBancaria {

    private BigDecimal taxaRendimentoMensal;
    private int diaAniversario;

    public ContaPoupanca() {
        super();
        this.taxaRendimentoMensal = bd(0.006);
        this.diaAniversario = 1;
    }

    public ContaPoupanca(String numero, String titular, BigDecimal saldo, boolean ativa,
                         BigDecimal taxaRendimentoMensal, int diaAniversario) {
        super(numero, titular, saldo, ativa);
        setTaxaRendimentoMensal(taxaRendimentoMensal);
        setDiaAniversario(diaAniversario);
    }

    public BigDecimal getTaxaRendimentoMensal() { return taxaRendimentoMensal; }
    public void setTaxaRendimentoMensal(BigDecimal taxaRendimentoMensal) {
        if (taxaRendimentoMensal == null || taxaRendimentoMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de rendimento não pode ser negativa.");
        }
        this.taxaRendimentoMensal = normalize(taxaRendimentoMensal);
    }

    public int getDiaAniversario() { return diaAniversario; }
    public void setDiaAniversario(int diaAniversario) {
        if (diaAniversario < 1 || diaAniversario > 31) {
            throw new IllegalArgumentException("Dia de aniversário deve estar entre 1 e 31.");
        }
        this.diaAniversario = diaAniversario;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo  : Conta Poupança");
        System.out.println("Taxa Rendimento Mensal: " + taxaRendimentoMensal);
        System.out.println("Dia de Aniversário    : " + diaAniversario);
    }

    public BigDecimal calcularSaldoProjetadoComRendimentoPadrao() {
        return super.calcularSaldoProjetado(taxaRendimentoMensal);
    }

    private BigDecimal bDecimal(double v) { 
        return super.bd(v); 
    }
}
