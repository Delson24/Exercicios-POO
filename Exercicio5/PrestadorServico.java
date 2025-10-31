package Exercicio5;
import java.io.Serializable;

public class PrestadorServico implements ServicoPagavel, Serializable {
    private String empresa;
    private double custoHora;
    private int horasFaturadas;

    public PrestadorServico(String empresa, double custoHora, int horasFaturadas) {
        this.empresa = empresa;
        this.custoHora = custoHora;
        this.horasFaturadas = horasFaturadas;
    }

    @Override
    public double calcularValorDevido() {
        return custoHora * horasFaturadas;
    }

    @Override
    public String toString() {
        return "Empresa: " + empresa + ", Custo/Hora: " + String.format("%.2f", custoHora) +
                ", Horas: " + horasFaturadas;
    }
}