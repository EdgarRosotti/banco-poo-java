package banco;

public abstract class Conta {
    private String titular;
    protected double saldo; // protected para as filhas acessarem

    public Conta(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para depósito.");
            return;
        }
        saldo += valor;
    }

    // Cada tipo de conta saca de um jeito → polimorfismo aqui
    public abstract boolean sacar(double valor);

    // Padrão: pagar boleto é só sacar
    public boolean pagarBoleto(double valor) {
        return sacar(valor);
    }

    // Só para exibição
    public abstract String getTipo();
}
