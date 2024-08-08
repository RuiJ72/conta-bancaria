package dio.agenciabancaria;

import dio.agenciabancaria.utilities.Utils;

public class Conta {

    private static int contadorDeContas = 1;

    private final int numeroConta;
    private Pessoa pessoa;
    private Double saldo;

    public Conta(Pessoa pessoa) {
        this.numeroConta = contadorDeContas++;
        this.pessoa = pessoa;
        this.saldo = 0.0;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Double getSaldo() {
        return saldo;
    }

    private void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return String.format(
                "\nNúmero da Conta: %d\nNome: %s\nCPF: %s\nEmail: %s\nSaldo: %s\n",
                this.numeroConta,
                this.pessoa.getNome(),
                this.pessoa.getCpf(),
                this.pessoa.getEmail(),
                Utils.doubleToString(this.saldo)
        );
    }

    public void depositar(Double valor) {
        if (valor > 0) {
            setSaldo(getSaldo() + valor);
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public boolean sacar(Double valor) {
        if (valor > 0 && this.saldo >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque realizado com sucesso.");
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }

    public boolean transferir(Conta contaDestino, Double valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso.");
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }
}
