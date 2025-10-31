package Exercicio4;

import java.io.Serializable;

public class Colaborador implements Serializable {
    private String nome;
    protected double salarioAtual;

    public Colaborador(String nome, double salarioAtual) {
        this.nome = nome;
        this.salarioAtual = salarioAtual;
    }

    public double getSalarioAtual() { return salarioAtual; }
    public String getNome() { return nome; }
}