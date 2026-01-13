package com.bancodoed.banco_api;

public class ContaCorrente extends Conta {

    private double limiteChequeEspecial;
    private double chequeEspecialUsado;

    public ContaCorrente(String titular, double saldoInicial, double limiteChequeEspecial) {
        super(titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.chequeEspecialUsado = 0;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public double getChequeEspecialUsado() {
        return chequeEspecialUsado;
    }

    public double getChequeEspecialDisponivel() {
        return limiteChequeEspecial - chequeEspecialUsado;
    }

    public boolean estaUsandoChequeEspecial() {
        return chequeEspecialUsado > 0;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0) return false;

        double disponivel = saldo + getChequeEspecialDisponivel();
        if (valor > disponivel) {
            return false; // nem com cheque especial
        }

        if (valor <= saldo) {
            saldo -= valor; // usa só saldo normal
        } else {
            double restante = valor - saldo;
            saldo = 0;
            chequeEspecialUsado += restante; // entra no especial
        }
        return true;
    }

    @Override
    public String getTipo() {
        return "Conta Corrente";
    }
}
