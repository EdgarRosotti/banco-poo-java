package banco;
import java.util.Scanner;

public class Main {
        

    public static void main(String[] args) throws Exception {
        // Contas prontas
        Conta conta1 = new Conta("João", 1000.0, 500.0);
        Conta conta2 = new Conta("Maria", 2500.0, 1000.0);
        Conta conta3 = new Conta("Edgar", 500.0, 200.0);
        // Guardando as contas em um array
        Conta[] contas = { conta1, conta2, conta3 };
        while (true) { 
            System.out.println("Deseja Abrir uma Conta Corrente?(1)\n" + "Deseja Logar em sua Conta?(2)\n" + "Sair?(3)\n");
            Scanner scanner = new Scanner(System.in);
            int opcao = scanner.nextInt();
            if (opcao == 1) {
                System.out.println("Funcionalidade de abertura de conta ainda não implementada.\n");
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

    // Menu da conta
    public static void menuConta(Conta conta, Scanner scanner) {
        while (true) {
            limparTela();

            System.out.println("===== CONTA CORRENTE =====");
            System.out.println("Titular: " + conta.getTitular());
            System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
            System.out.println();
            System.out.println("1 - Consultar cheque especial");
            System.out.println("2 - Solicitar cheque especial");
            System.out.println("3 - Pagar boleto");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // CONSULTAR CHEQUE ESPECIAL
                    limparTela();
                    double limiteTotal = conta.getEspecial();   // limite total configurado
                    double usado = conta.getVespecial();        // quanto já usou
                    double disponivel = limiteTotal - usado;    // quanto ainda pode usar

                    System.out.println("=== Cheque Especial ===");
                    System.out.printf("Limite total:     R$ %.2f%n", limiteTotal);
                    System.out.printf("Já utilizado:     R$ %.2f%n", usado);
                    System.out.printf("Limite disponível:R$ %.2f%n", disponivel);
                    pausar(scanner);
                    break;

                case 2:
                    // SOLICITAR CHEQUE ESPECIAL
                    limparTela();

                    limiteTotal = conta.getEspecial();
                    usado = conta.getVespecial();
                    disponivel = limiteTotal - usado;

                    if (disponivel <= 0) {
                        System.out.println("Você não possui limite de cheque especial disponível.");
                        pausar(scanner);
                        break;
                    }

                    double valorCheque;

                    do {
                        limparTela();
                        System.out.println("=== Solicitar Cheque Especial ===");
                        System.out.printf("Limite total:     R$ %.2f%n", limiteTotal);
                        System.out.printf("Já utilizado:     R$ %.2f%n", usado);
                        System.out.printf("Disponível:       R$ %.2f%n%n", disponivel);

                        System.out.print("Informe o valor de cheque especial que deseja usar: ");
                        valorCheque = scanner.nextDouble();

                        if (valorCheque > disponivel) {
                            System.out.println("\nOpção inválida. Limite do cheque especial excedido.");
                            System.out.println("Tente novamente.");
                            pausar(scanner);
                        }

                    } while (valorCheque > disponivel);

                    // valor válido, atualiza saldo e usado
                    conta.setSaldo(conta.getSaldo() + valorCheque);
                    conta.setVespecial(conta.getVespecial() + valorCheque);

                    System.out.println("\nCheque especial liberado e adicionado ao saldo.");
                    pausar(scanner);
                    break;

                case 3:
                    // PAGAR BOLETO
                    limparTela();
                    System.out.println("=== Pagar Boleto ===");
                    System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());

                    limiteTotal = conta.getEspecial();
                    usado = conta.getVespecial();
                    disponivel = limiteTotal - usado;

                    System.out.printf("Limite de cheque especial disponível: R$ %.2f%n%n", disponivel);

                    System.out.print("Informe o valor do boleto: ");
                    double valorBoleto = scanner.nextDouble();

                    if (valorBoleto <= 0) {
                        System.out.println("Valor inválido para boleto.");
                        pausar(scanner);
                        break;
                    }

                    double saldoAtual = conta.getSaldo();
                    double totalDisponivel = saldoAtual + disponivel;

                    if (valorBoleto > totalDisponivel) {
                        System.out.println("\nSaldo insuficiente (mesmo com cheque especial).");
                        pausar(scanner);
                        break;
                    }

                    // Primeiro usa o saldo normal
                    if (valorBoleto <= saldoAtual) {
                        conta.setSaldo(saldoAtual - valorBoleto);
                    } else {
                        // Usa todo saldo e o restante vem do cheque especial
                        double restante = valorBoleto - saldoAtual;
                        conta.setSaldo(0);
                        conta.setVespecial(conta.getVespecial() + restante);
                    }

                    System.out.println("\nBoleto pago com sucesso.");
                    if (conta.getVespecial() > 0) {
                        System.out.println("Atenção: você está usando o cheque especial!");
                    }
                    pausar(scanner);
                    break;

                case 4:
                    // SAIR DO MENU DA CONTA
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

