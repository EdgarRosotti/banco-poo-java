package com.bancodoed.banco_api;

public class ContaPoupanca extends Conta {

    private double taxaRendimentoAnual; // ex: 0.05 = 5% ao ano (só exemplo)

    public ContaPoupanca(String titular, double saldoInicial, double taxaRendimentoAnual) {
        super(titular, saldoInicial);
        this.taxaRendimentoAnual = taxaRendimentoAnual;
    }

    public double getTaxaRendimentoAnual() {
        return taxaRendimentoAnual;
    }

    public void aplicarRendimento() {
        saldo += saldo * taxaRendimentoAnual;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            return false; // não deixa negativar, sem cheque especial
        }
        saldo -= valor;
        return true;
    }

    @Override
    public String getTipo() {
        return "Conta Poupança";
    }
}
