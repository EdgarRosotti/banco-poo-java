package banco;
public class Conta {
    private String titular;
    private double saldo;
    private double especial; //cheque especial
    private double vespecial; //Verificar cheque especial



   // construtor com parâmetros
    public Conta(String titular, double saldo, double especial) {
        this.titular = titular;
        this.saldo = saldo;
        this.especial = especial;
        this.vespecial = 0;
    }

    // Quanto ainda tenho de cheque especial disponível
    public double consultarChequeEspecialDisponivel() {
        return especial - vespecial;
    }
    // Depositar dinheiro (não usa cheque especial, só soma no saldo)
    public boolean depositar(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.saldo += valor;

        // Se estava usando cheque especial (saldo negativo), o depósito
        // poderia primeiro cobrir o "rombo", mas como sua lógica hoje
        // controla isso em vespecial, vamos manter simples: só soma no saldo.
        return true;
    }

    // Sacar dinheiro (permitindo usar cheque especial)
    public boolean sacar(double valor) {
        if (valor <= 0) {
            return false;
        }

        double disponivelCheque = consultarChequeEspecialDisponivel();
        double totalDisponivel = saldo + disponivelCheque;

        if (valor > totalDisponivel) {
            return false; // nem com cheque especial dá pra sacar
        }

        if (valor <= saldo) {
            // saca só do saldo
            saldo -= valor;
        } else {
            // usa todo saldo e o resto vem do cheque especial
            double restante = valor - saldo;
            saldo = 0;
            vespecial += restante;
        }

        return true;
    }

    // Verificar se está usando cheque especial
    public boolean estaUsandoChequeEspecial() {
        return vespecial > 0;
    }


    // Tenta usar o cheque especial; se conseguir, retorna true
    public boolean solicitarChequeEspecial(double valor) {
        double disponivel = consultarChequeEspecialDisponivel();

        if (valor <= 0 || valor > disponivel) {
            return false; // inválido ou passou do limite
        }

        saldo += valor;      // entra dinheiro na conta
        vespecial += valor;  // registra que usou esse valor do limite
        return true;
    }

    // Pagar boleto usando saldo + cheque especial
    public boolean pagarBoleto(double valor) {
        if (valor <= 0) {
            return false;
        }

        double disponivelCheque = consultarChequeEspecialDisponivel();
        double totalDisponivel = saldo + disponivelCheque;

        if (valor > totalDisponivel) {
            return false; // nem com cheque especial dá
        }

        if (valor <= saldo) {
            // paga só com saldo
            saldo -= valor;
        } else {
            // usa todo saldo e o resto vem do cheque especial
            double restante = valor - saldo;
            saldo = 0;
            vespecial += restante;
        }

        return true;
    }



    // getters e setters
    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public double getEspecial() {
        return especial;
    }
    public void setEspecial(double especial) {
        this.especial = especial;
    }
    public double getVespecial() {
        return vespecial;
    }
    public void setVespecial(double vespecial) {
        this.vespecial = vespecial;
    }


}
