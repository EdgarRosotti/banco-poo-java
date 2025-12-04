package banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
        

    public static void main(String[] args) throws Exception {
        // Lista para armazenar as contas
        List<Conta> contas = new ArrayList<>();

        // Contas prontas iniciais
        contas.add(new Conta("João", 1000.0, 500.0));
        contas.add(new Conta("Maria", 2500.0, 1000.0));
        contas.add(new Conta("Edgar", 500.0, 200.0));
        while (true) { 
            System.out.println("Deseja Abrir uma Conta Corrente?(1)\n" + "Deseja Logar em sua Conta?(2)\n" + "Sair?(3)\n");
            Scanner scanner = new Scanner(System.in);
            int opcao = scanner.nextInt();
            if (opcao == 1) {
                limparTela();
                System.out.println("=== Abertura de Conta ===");

                System.out.print("Digite o nome do titular da conta: ");
                String nomeTitular = scanner.next(); // por enquanto só 1 palavra

                System.out.print("Digite o saldo inicial: ");
                double saldoInicial = scanner.nextDouble();

                System.out.print("Digite o limite de cheque especial: ");
                double limiteEspecial = scanner.nextDouble();

                Conta novaConta = new Conta(nomeTitular, saldoInicial, limiteEspecial);
                contas.add(novaConta);

                System.out.println("\nConta criada com sucesso para " + nomeTitular + "!");
                pausar(scanner);
            } 
            else if (opcao == 2) {
                System.out.println("Digite o nome do titular da conta:");
                String nomeDigitado = scanner.next(); 

                Conta contaLogada = buscarContaPorNome(contas, nomeDigitado);

                if (contaLogada != null) {
                    // menu da conta
                    menuConta(contaLogada, scanner);
                    
                }else {
                    limparTela();
                    scanner.nextLine();
                    System.out.println("Conta não encontrada para o nome: " + nomeDigitado);
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
            } 
            else if (opcao == 3) {
                limparTela();
                System.out.println("Encerrando o programa.");
                break; 
            }




            else {
                System.out.println("Opção inválida.\n");
                System.out.println("Deseja Tentar Novamente?(s ou n).\n");
                String tentarNovamente = scanner.next();
                if (tentarNovamente.equalsIgnoreCase("n")) {
                    System.out.println("Encerrando o programa.");
                    break; 
                }
        
            }
        }
    }

    // Método para buscar conta por nome em uma lista
    public static Conta buscarContaPorNome(List<Conta> contas, String nomeDigitado) {
        for (Conta conta : contas) {
            if (conta.getTitular().equalsIgnoreCase(nomeDigitado)) {
                return conta;
            }
        }
        return null;
    }
    // Menu da conta
    public static void menuConta(Conta conta, Scanner scanner) {
    while (true) {
        limparTela();

        System.out.println("===== CONTA CORRENTE =====");
        System.out.println("Titular: " + conta.getTitular());
        System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
        System.out.println();
        System.out.println("1 - Depositar");
        System.out.println("2 - Sacar");
        System.out.println("3 - Consultar cheque especial");
        System.out.println("4 - Solicitar cheque especial");
        System.out.println("5 - Pagar boleto");
        System.out.println("6 - Verificar se está usando cheque especial");
        System.out.println("7 - Sair");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1: { // DEPOSITAR
                limparTela();
                System.out.println("=== Depósito ===");
                System.out.print("Informe o valor para depósito: ");
                double valorDeposito = scanner.nextDouble();

                if (conta.depositar(valorDeposito)) {
                    System.out.println("\nDepósito realizado com sucesso!");
                } else {
                    System.out.println("\nValor inválido para depósito.");
                }
                pausar(scanner);
                break;
                }

            case 2: { // SACAR
                limparTela();
                System.out.println("=== Saque ===");
                System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
                System.out.printf("Cheque especial disponível: R$ %.2f%n",
                        conta.consultarChequeEspecialDisponivel());
                System.out.print("\nInforme o valor para saque: ");
                double valorSaque = scanner.nextDouble();

                if (conta.sacar(valorSaque)) {
                    System.out.println("\nSaque realizado com sucesso.");
                    if (conta.estaUsandoChequeEspecial()) {
                        System.out.println("Atenção: você está usando cheque especial!");
                    }
                } else {
                    System.out.println("\nNão foi possível realizar o saque (valor inválido ou saldo insuficiente).");
                }
                pausar(scanner);
                break;
                }

            case 3: { // CONSULTAR CHEQUE ESPECIAL
                limparTela();
                double limiteTotal = conta.getEspecial();
                double usado = conta.getVespecial();
                double disponivel = conta.consultarChequeEspecialDisponivel();

                System.out.println("=== Cheque Especial ===");
                System.out.printf("Limite total:      R$ %.2f%n", limiteTotal);
                System.out.printf("Já utilizado:      R$ %.2f%n", usado);
                System.out.printf("Limite disponível: R$ %.2f%n", disponivel);
                pausar(scanner);
                break;
                }

            case 4: { // SOLICITAR CHEQUE ESPECIAL
                limparTela();

                double disponivelCheque = conta.consultarChequeEspecialDisponivel();
                if (disponivelCheque <= 0) {
                    System.out.println("Você não possui limite de cheque especial disponível.");
                    pausar(scanner);
                    break;
                }

                double valorCheque;
                boolean sucesso;

                do {
                    limparTela();
                    System.out.println("=== Solicitar Cheque Especial ===");
                    System.out.printf("Limite total:      R$ %.2f%n", conta.getEspecial());
                    System.out.printf("Já utilizado:      R$ %.2f%n", conta.getVespecial());
                    System.out.printf("Disponível:        R$ %.2f%n%n", conta.consultarChequeEspecialDisponivel());

                    System.out.print("Informe o valor de cheque especial que deseja usar: ");
                    valorCheque = scanner.nextDouble();

                    sucesso = conta.solicitarChequeEspecial(valorCheque);

                    if (!sucesso) {
                        System.out.println("\nOpção inválida. Limite do cheque especial excedido ou valor inválido.");
                        System.out.println("Tente novamente.");
                        pausar(scanner);
                    }
                } while (!sucesso);

                System.out.println("\nCheque especial liberado e adicionado ao saldo.");
                pausar(scanner);
                break;
                }

            case 5: { // PAGAR BOLETO
                limparTela();
                System.out.println("=== Pagar Boleto ===");
                System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
                System.out.printf("Limite de cheque especial disponível: R$ %.2f%n%n",
                        conta.consultarChequeEspecialDisponivel());

                System.out.print("Informe o valor do boleto: ");
                double valorBoleto = scanner.nextDouble();

                boolean pago = conta.pagarBoleto(valorBoleto);

                if (!pago) {
                    System.out.println("\nNão foi possível pagar o boleto (valor inválido ou saldo insuficiente).");
                    pausar(scanner);
                    break;
                }

                System.out.println("\nBoleto pago com sucesso.");
                if (conta.estaUsandoChequeEspecial()) {
                    System.out.println("Atenção: você está usando cheque especial!");
                }
                pausar(scanner);
                break;
                }

            case 6: { // VERIFICAR SE ESTÁ USANDO CHEQUE ESPECIAL
                limparTela();
                if (conta.estaUsandoChequeEspecial()) {
                    System.out.println("Você está usando cheque especial no momento.");
                } else {
                    System.out.println("Você NÃO está usando cheque especial.");
                }
                pausar(scanner);
                break;
                }

            case 7:
                return;

            default:
                System.out.println("Opção inválida.");
                pausar(scanner);
                break;
            }
        }
    }



    // Método para pausar e esperar o usuário pressionar ENTER
    public static void pausar(Scanner scanner) {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine(); // Consumir a nova linha pendente
        scanner.nextLine(); // Esperar o ENTER
    }
    // Método para limpar a tela
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    // Método para buscar conta por nome
    public static Conta buscarContaPorNome(Conta[] contas, String nomeDigitado) {
        for (Conta conta : contas) {
            if (conta.getTitular().equalsIgnoreCase(nomeDigitado)) {
                return conta;
            }
        }
        return null;
    }

}

