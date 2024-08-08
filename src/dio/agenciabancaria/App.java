package dio.agenciabancaria;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Conta> contas = new ArrayList<>();

    public static void main(String[] args) {
        operacoes();
    }

    // Menu de operações
    private static void operacoes() {
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int operacao = obterOpcaoUsuario();

            switch (operacao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    depositar();
                    break;
                case 3:
                    sacar();
                    break;
                case 4:
                    transferir();
                    break;
                case 5:
                    listar();
                    break;
                case 6:
                    System.out.println("Obrigado pela utilização dos nossos serviços");
                    continuar = false;
                    break;
                default:
                    System.out.println("Operação inválida. Tente novamente.");
                    break;
            }
        }
    }

    // Exibe o menu principal
    private static void exibirMenu() {
        System.out.println("------------------------------------------------------");
        System.out.println("-------------Bem-vindo à nossa Agência---------------");
        System.out.println("------------------------------------------------------");
        System.out.println("***** Selecione a operação que deseja realizar *****");
        System.out.println("------------------------------------------------------");
        System.out.println("|   Opção 1 - Criar conta   |");
        System.out.println("|   Opção 2 - Depositar     |");
        System.out.println("|   Opção 3 - Sacar         |");
        System.out.println("|   Opção 4 - Transferir    |");
        System.out.println("|   Opção 5 - Listar        |");
        System.out.println("|   Opção 6 - Sair          |");
        System.out.println("------------------------------------------------------");
    }

    // Obtém a opção do usuário com tratamento de exceções
    private static int obterOpcaoUsuario() {
        int operacao = -1;
        while (operacao < 1 || operacao > 6) {
            System.out.print("Escolha uma opção: ");
            try {
                operacao = input.nextInt();
                input.nextLine(); // Limpa o buffer do scanner
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número entre 1 e 6.");
                input.nextLine(); // Limpa o buffer do scanner
            }
        }
        return operacao;
    }

    // Cria uma nova conta
    private static void criarConta() {
        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("CPF: ");
        String cpf = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        Pessoa pessoa = new Pessoa(nome, cpf, email);
        Conta conta = new Conta(pessoa);

        contas.add(conta);
        System.out.println("Conta criada com sucesso!");
    }

    // Encontra uma conta pelo número
    private static Conta encontrarConta(int numeroConta) {
        return contas.stream()
                .filter(c -> c.getNumeroConta() == numeroConta)
                .findFirst()
                .orElse(null);
    }

    // Realiza um depósito em uma conta
    private static void depositar() {
        Conta conta = obterConta();
        if (conta != null) {
            System.out.print("Insira o valor a ser depositado: ");
            double valorDeposito = obterValorPositivo();
            conta.depositar(valorDeposito);
        }
    }

    // Realiza um saque de uma conta
    private static void sacar() {
        Conta conta = obterConta();
        if (conta != null) {
            System.out.print("Insira o valor a sacar: ");
            double valorSaque = obterValorPositivo();
            if (conta.sacar(valorSaque)) {
                System.out.println("Saque efetuado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente para saque.");
            }
        }
    }

    // Realiza uma transferência entre contas
    private static void transferir() {
        System.out.print("Número da Conta de Origem: ");
        Conta contaOrigem = obterConta();
        if (contaOrigem != null) {
            System.out.print("Número da Conta do Destinatário: ");
            Conta contaDestinatario = obterConta();
            if (contaDestinatario != null && contaOrigem != contaDestinatario) {
                System.out.print("Valor da transferência: ");
                double valorTransferencia = obterValorPositivo();
                if (contaOrigem.transferir(contaDestinatario, valorTransferencia)) {
                    System.out.println("Transferência realizada com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente para transferência.");
                }
            } else {
                System.out.println("Conta de destino não encontrada ou é a mesma que a origem.");
            }
        }
    }

    // Obtém e valida um valor positivo
    private static double obterValorPositivo() {
        double valor = -1;
        while (valor <= 0) {
            try {
                valor = input.nextDouble();
                input.nextLine(); // Limpa o buffer do scanner
                if (valor <= 0) {
                    System.out.println("O valor deve ser positivo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um valor numérico.");
                input.nextLine(); // Limpa o buffer do scanner
            }
        }
        return valor;
    }

    // Obtém uma conta do usuário
    private static Conta obterConta() {
        System.out.print("Número da conta: ");
        int numeroConta = -1;
        while (numeroConta < 0) {
            try {
                numeroConta = input.nextInt();
                input.nextLine(); // Limpa o buffer do scanner
                if (numeroConta < 0) {
                    System.out.println("Número da conta deve ser positivo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número de conta válido.");
                input.nextLine(); // Limpa o buffer do scanner
            }
        }
        return encontrarConta(numeroConta);
    }

    // Lista todas as contas
    private static void listar() {
        if (contas.isEmpty()) {
            System.out.println("Não existem contas cadastradas!");
        } else {
            contas.forEach(System.out::println);
        }
    }
}
