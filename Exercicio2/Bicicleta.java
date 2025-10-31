package Exercicio2;

public class Bicicleta extends MeioDeTransporte {
    public Bicicleta(String nomeMarca) {
        super(nomeMarca);
    }
    @Override
    public String descreverMovimento() {
        return "A ser pedalada na ciclovia, silenciosamente.";
    }
}