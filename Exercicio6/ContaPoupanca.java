package Exercicio6;
import java.io.Serializable;

public class ContaPoupanca implements ContaBancaria, Serializable {
    private String numeroConta;
    private double saldo;
    private static final double PERCENTUAL_TAXA = 0.01; // Taxa de 1% sobre o saldo

    public ContaPoupanca(String numeroConta, double saldo) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    @Override
    public double calcularTaxa() {
        return saldo * PERCENTUAL_TAXA; // Taxa variável
    }

    @Override
    public double getSaldo() { return saldo; }
    public String getNumeroConta() { return numeroConta; }
}