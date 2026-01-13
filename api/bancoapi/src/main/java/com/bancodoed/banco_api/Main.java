package com.bancodoed.banco_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Conta> contas = new ArrayList<>();

        // Contas prontas de exemplo
        contas.add(new ContaCorrente("João", 1000.0, 500.0));
        contas.add(new ContaPoupanca("Maria", 2000.0, 0.05));
        contas.add(new ContaCorrente("Edgar", 500.0, 200.0));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            limparTela();
            System.out.println("=== Banco do ED (console) ===");
            System.out.println("1 - Criar nova conta");
            System.out.println("2 - Logar em uma conta existente");
            System.out.println("3 - Listar contas (debug)");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            if (opcao == 1) {
                criarConta(contas, scanner);
            } else if (opcao == 2) {
                logar(contas, scanner);
            } else if (opcao == 3) {
                listarContas(contas, scanner);
            } else if (opcao == 4) {
                System.out.println("Encerrando o programa.");
                break;
            } else {
                System.out.println("Opção inválida.");
                pausar(scanner);
            }
        }

        scanner.close();
    }

    // ========== Fluxo de login ==========

    private static void logar(List<Conta> contas, Scanner scanner) {
        limparTela();
        System.out.print("Digite o nome do titular: ");
        String nome = scanner.next();

        Conta conta = buscarContaPorNome(contas, nome);

        if (conta == null) {
            System.out.println("Conta não encontrada para o titular: " + nome);
            pausar(scanner);
            return;
        }

        menuConta(conta, scanner);
    }

    // ========== Criação de conta ==========

    private static void criarConta(List<Conta> contas, Scanner scanner) {
        limparTela();
        System.out.println("=== Abertura de Conta ===");
        System.out.print("Nome do titular: ");
        String nome = scanner.next();

        System.out.println("Tipo de conta:");
        System.out.println("1 - Corrente (com cheque especial)");
        System.out.println("2 - Poupança (com rendimento)");
        System.out.print("Escolha: ");
        int tipo = scanner.nextInt();

        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();

        Conta novaConta = null;

        if (tipo == 1) {
            System.out.print("Limite de cheque especial: ");
            double limite = scanner.nextDouble();
            novaConta = new ContaCorrente(nome, saldoInicial, limite);
        } else if (tipo == 2) {
            System.out.print("Taxa de rendimento anual (ex: 0.05 para 5%): ");
            double taxa = scanner.nextDouble();
            novaConta = new ContaPoupanca(nome, saldoInicial, taxa);
        } else {
            System.out.println("Tipo inválido. Conta não criada.");
            pausar(scanner);
            return;
        }

        contas.add(novaConta);
        System.out.println("Conta criada com sucesso para " + nome + " (" + novaConta.getTipo() + ").");
        pausar(scanner);
    }

    // ========== Menu da conta (polimorfismo aqui) ==========

    private static void menuConta(Conta conta, Scanner scanner) {
        while (true) {
            limparTela();
            System.out.println("=== Conta de " + conta.getTitular() + " ===");
            System.out.println("Tipo: " + conta.getTipo());
            System.out.printf("Saldo: R$ %.2f%n", conta.getSaldo());
            System.out.println();
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Pagar boleto");
            System.out.println("4 - Consultar cheque especial (se for corrente)");
            System.out.println("5 - Ver se está usando cheque especial");
            System.out.println("6 - Voltar ao menu principal");
            System.out.print("Escolha: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1: {
                    System.out.print("Valor do depósito: ");
                    double valor = scanner.nextDouble();
                    conta.depositar(valor);
                    System.out.println("Depósito realizado.");
                    pausar(scanner);
                    break;
                }
                case 2: {
                    System.out.print("Valor do saque: ");
                    double valor = scanner.nextDouble();
                    boolean ok = conta.sacar(valor); // POLIMORFISMO: cada conta saca do seu jeito
                    if (ok) {
                        System.out.println("Saque realizado.");
                    } else {
                        System.out.println("Não foi possível sacar (valor inválido ou limite insuficiente).");
                    }
                    pausar(scanner);
                    break;
                }
                case 3: {
                    System.out.print("Valor do boleto: ");
                    double valor = scanner.nextDouble();
                    boolean ok = conta.pagarBoleto(valor); // também polimórfico
                    if (ok) {
                        System.out.println("Boleto pago.");
                    } else {
                        System.out.println("Não foi possível pagar o boleto.");
                    }
                    pausar(scanner);
                    break;
                }
                case 4: {
                    if (conta instanceof ContaCorrente cc) {
                        System.out.println("=== Cheque Especial ===");
                        System.out.printf("Limite total:      R$ %.2f%n", cc.getLimiteChequeEspecial());
                        System.out.printf("Já utilizado:      R$ %.2f%n", cc.getChequeEspecialUsado());
                        System.out.printf("Disponível:        R$ %.2f%n", cc.getChequeEspecialDisponivel());
                    } else {
                        System.out.println("Essa conta não possui cheque especial.");
                    }
                    pausar(scanner);
                    break;
                }
                case 5: {
                    if (conta instanceof ContaCorrente cc) {
                        if (cc.estaUsandoChequeEspecial()) {
                            System.out.println("Você está usando cheque especial.");
                        } else {
                            System.out.println("Você NÃO está usando cheque especial.");
                        }
                    } else {
                        System.out.println("Essa conta não possui cheque especial.");
                    }
                    pausar(scanner);
                    break;
                }
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida.");
                    pausar(scanner);
                    break;
            }
        }
    }

    // ========== Utilitários ==========

    private static Conta buscarContaPorNome(List<Conta> contas, String nome) {
        for (Conta c : contas) {
            if (c.getTitular().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    private static void listarContas(List<Conta> contas, Scanner scanner) {
        limparTela();
        System.out.println("=== Contas cadastradas ===");
        for (Conta c : contas) {
            System.out.printf("- %s (%s) | Saldo: R$ %.2f%n",
                    c.getTitular(), c.getTipo(), c.getSaldo());
        }
        pausar(scanner);
    }

    private static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // ignora
        }
    }

    private static void pausar(Scanner scanner) {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine(); // consome resto da linha do último nextInt/nextDouble
        scanner.nextLine();
    }
}
