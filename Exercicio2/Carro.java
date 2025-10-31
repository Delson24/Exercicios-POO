package Exercicio2;

public class Carro extends MeioDeTransporte {
    public Carro(String nomeMarca) {
        super(nomeMarca);
    }
    @Override
    public String descreverMovimento() {
        return "A acelerar rapidamente na estrada.";
    }
}