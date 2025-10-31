package Exercicio2;

import java.io.Serializable;

public abstract class MeioDeTransporte implements Serializable {
    private String nomeMarca;

    public MeioDeTransporte(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    public abstract String descreverMovimento();
    public String getNomeMarca() { return nomeMarca; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + nomeMarca;
    }
}