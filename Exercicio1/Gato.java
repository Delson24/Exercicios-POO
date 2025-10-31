package Exercicio1;

public class Gato extends EspecieAnimal {
    public Gato(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public String emitirSom() {
        return "Miau!";
    }
}