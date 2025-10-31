package Exercicio3;

import Exercicio3.PessoaGenerica;

public class Estudante extends PessoaGenerica {
    private String matricula;

    public Estudante(String nome, int idade, String matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }

    public String getMatricula() { return matricula; }

    @Override
    public String toString() {
        // Usa o toString da Pessoa e adiciona a Matrícula
        return super.toString() + ", Matrícula: " + matricula;
    }
}