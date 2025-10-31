package Exercicio6;
import java.io.Serializable;

public class ContaCorrente implements ContaBancaria, Serializable {
    private String numeroConta;
    private double saldo;
    private static final double TAXA_FIXA = 5.0; // Taxa de manutenção fixa

    public ContaCorrente(String numeroConta, double saldo) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    @Override
    public double calcularTaxa() {
        return TAXA_FIXA;
    }

    @Override
    public double getSaldo() { return saldo; }
    public String getNumeroConta() { return numeroConta; }
}