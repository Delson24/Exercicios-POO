package Exercicio1;

import java.io.Serializable;

public abstract class EspecieAnimal implements Serializable {
    private String nome;
    private int idade;

    public EspecieAnimal(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public abstract String emitirSom();

    public String getNome() { return nome; }
    public int getIdade() { return idade; }

    @Override
    public String toString() {
        return nome + " (" + idade + " anos, Som: " + emitirSom() + ")";
    }
}