package Exercicio5;
import java.io.Serializable;

public class Assalariado implements ServicoPagavel, Serializable {
    private String nome;
    private double salarioMensal;

    public Assalariado(String nome, double salarioMensal) {
        this.nome = nome;
        this.salarioMensal = salarioMensal;
    }

    @Override
    public double calcularValorDevido() {
        return salarioMensal;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Salário Base: " + String.format("%.2f", salarioMensal);
    }
}