package Exercicio3;

import java.io.Serializable;

public class PessoaGenerica implements Serializable {
    private String nome;
    private int idade;

    public PessoaGenerica(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }

    @Override
    public String toString() {
        return "Pessoa: " + nome + " (Idade: " + idade + ")";
    }
}