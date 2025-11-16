package com.projeto_oo.domain;

import java.math.BigDecimal;

public class ContaCorrente extends ContaBancaria {

    private BigDecimal limiteChequeEspecial;
    private BigDecimal tarifaMensal;

    public ContaCorrente() {
        super();
        this.limiteChequeEspecial = bd(500);
        this.tarifaMensal = bd(19.90);
    }

    public ContaCorrente(String numero, String titular, BigDecimal saldo, boolean ativa,
                         BigDecimal limiteChequeEspecial, BigDecimal tarifaMensal) {
        super(numero, titular, saldo, ativa);
        setLimiteChequeEspecial(limiteChequeEspecial);
        setTarifaMensal(tarifaMensal);
    }

    public BigDecimal getLimiteChequeEspecial() { return limiteChequeEspecial; }
    public void setLimiteChequeEspecial(BigDecimal limiteChequeEspecial) {
        if (limiteChequeEspecial == null || limiteChequeEspecial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Limite do cheque especial não pode ser negativo.");
        }
        this.limiteChequeEspecial = normalize(limiteChequeEspecial);
    }

    public BigDecimal getTarifaMensal() { return tarifaMensal; }
    public void setTarifaMensal(BigDecimal tarifaMensal) {
        if (tarifaMensal == null || tarifaMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tarifa mensal não pode ser negativa.");
        }
        this.tarifaMensal = normalize(tarifaMensal);
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarAtiva();
        validarValorPositivo(valor, "Saque");

        BigDecimal disponibilidade = getSaldo().add(limiteChequeEspecial);
        if (disponibilidade.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo + cheque especial insuficiente.");
        }
        super.setSaldo(getSaldo().subtract(valor));
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo  : Conta Corrente");
        System.out.println("Limite Cheque Especial: R$ " + limiteChequeEspecial);
        System.out.println("Tarifa Mensal         : R$ " + tarifaMensal);
    }

    public BigDecimal calcularSaldoProjetadoConsiderandoTarifa() {
        return super.calcularSaldoProjetado(BigDecimal.ZERO, tarifaMensal);
    }

    private BigDecimal bDecimal(double v) { return super.bd(v); }
}
