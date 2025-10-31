package Exercicio1;

public class Cachorro extends EspecieAnimal {
    public Cachorro(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public String emitirSom() {
        return "Au Au!";
    }
}