public class Conta {
    private String titular;
    private double saldo;
    private double especial; //cheque especial
    private double depositar;
    private double sacar;
    private double boleto;
    private double vespecial; //Verificar cheque especial



   // construtor com parâmetros
    public Conta(String titular, double saldo, double especial) {
        this.titular = titular;
        this.saldo = saldo;
        this.especial = especial;
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
    public double getDepositar() {
        return depositar;
    }
    public void setDepositar(double depositar) {
        this.depositar = depositar;
    }
    public double getSacar() {
        return sacar;
    }
    public void setSacar(double sacar) {
        this.sacar = sacar;
    }
    public double getBoleto() {
        return boleto;
    }
    public void setBoleto(double boleto) {
        this.boleto = boleto;
    }
    public double getVespecial() {
        return vespecial;
    }
    public void setVespecial(double vespecial) {
        this.vespecial = vespecial;
    }


}
