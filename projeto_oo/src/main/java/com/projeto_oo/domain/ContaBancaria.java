package com.projeto_oo.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ContaBancaria {
    private String numero;
    private String titular;
    private BigDecimal saldo;
    private boolean ativa;

    public ContaBancaria() {
        this.numero  = "0000-0";
        this.titular = "Titular Padrão";
        this.saldo   = bd(0);
        this.ativa   = true;
    }

    public ContaBancaria(String numero, String titular, BigDecimal saldo, boolean ativa) {
        setNumero(numero);
        setTitular(titular);
        setSaldo(saldo);
        this.ativa = ativa;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio.");
        }
        this.numero = numero.trim();
    }

    public String getTitular() { return titular; }
    public void setTitular(String titular) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("Titular não pode ser vazio.");
        }
        this.titular = titular.trim();
    }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo não pode ser negativo.");
        }
        this.saldo = normalize(saldo);
    }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public void depositar(BigDecimal valor) {
        validarAtiva();
        validarValorPositivo(valor, "Depósito");
        this.saldo = normalize(this.saldo.add(valor));
    }

    public void sacar(BigDecimal valor) {
        validarAtiva();
        validarValorPositivo(valor, "Saque");
        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.saldo = normalize(this.saldo.subtract(valor));
    }

    public BigDecimal calcularSaldoProjetado() {
        return normalize(this.saldo);
    }

    public BigDecimal calcularSaldoProjetado(BigDecimal rendimentoPercentual) {
        Objects.requireNonNull(rendimentoPercentual, "Rendimento não pode ser nulo.");
        return normalize(this.saldo.multiply(BigDecimal.ONE.add(rendimentoPercentual)));
    }

    public BigDecimal calcularSaldoProjetado(BigDecimal rendimentoPercentual, BigDecimal tarifaFixa) {
        Objects.requireNonNull(rendimentoPercentual, "Rendimento não pode ser nulo.");
        Objects.requireNonNull(tarifaFixa, "Tarifa não pode ser nula.");
        BigDecimal projetado = calcularSaldoProjetado(rendimentoPercentual).subtract(tarifaFixa);
        if (projetado.compareTo(BigDecimal.ZERO) < 0) projetado = BigDecimal.ZERO;
        return normalize(projetado);
    }

    public void exibirInformacoes() {
        System.out.println("=== Conta Bancária ===");
        System.out.println("Número : " + numero);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo  : R$ " + saldo);
        System.out.println("Ativa  : " + (ativa ? "Sim" : "Não"));
    }

    protected void validarAtiva() {
        if (!ativa) throw new IllegalStateException("Conta inativa.");
    }

    protected void validarValorPositivo(BigDecimal valor, String contexto) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(contexto + " deve ser maior que zero.");
        }
    }

    protected BigDecimal normalize(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_UP);
    }

    protected BigDecimal bd(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP);
    }
}
