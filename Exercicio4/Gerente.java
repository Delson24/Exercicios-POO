package Exercicio4;

public class Gerente extends Colaborador {
    public Gerente(String nome, double salarioAtual) {
        super(nome, salarioAtual);
    }

    public void darAumento(double percentual) {
        this.salarioAtual *= (1 + percentual / 100.0);
    }
}