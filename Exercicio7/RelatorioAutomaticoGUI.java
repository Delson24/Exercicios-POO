package Exercicio7;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Imports necessários para simular as listas de outros pacotes
import Exercicio1.Cachorro;
import Exercicio3.Estudante;
import Exercicio4.Gerente;

public class RelatorioAutomaticoGUI extends JFrame {

    // Listas genéricas para simular dados de vários exercícios
    private List<Object> listaAnimais = criarListaTesteAnimais();
    private List<Object> listaPessoas = criarListaTestePessoas();
    private List<Object> listaFuncionarios = criarListaTesteFuncionarios();

    private JComboBox<String> cmbTipo;
    private JTextArea areaTextoRelatorio = new JTextArea(20, 40);

    public RelatorioAutomaticoGUI() {
        super("Gerador de Relatórios Automático");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 480);

        configurarLayout();
        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        cmbTipo = new JComboBox<>(new String[]{"Animais (Ex1)", "Pessoas (Ex3)", "Funcionários (Ex4)"});

        JPanel painelControle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnGerar = new JButton("Gerar Relatório");
        JButton btnExportar = new JButton("Exportar para TXT");

        btnGerar.addActionListener(e -> gerarRelatorio((String) cmbTipo.getSelectedItem()));
        btnExportar.addActionListener(e -> exportarRelatorio());

        painelControle.add(new JLabel("Tipo de Dados:"));
        painelControle.add(cmbTipo);
        painelControle.add(btnGerar);
        painelControle.add(btnExportar);

        areaTextoRelatorio.setEditable(false);

        add(painelControle, BorderLayout.NORTH);
        add(new JScrollPane(areaTextoRelatorio), BorderLayout.CENTER);
    }

    private void gerarRelatorio(String tipoSelecionado) {
        areaTextoRelatorio.setText("--- Relatório de " + tipoSelecionado + " ---\n\n");
        List<?> lista = null;

        // Seleção da lista baseada na opção da ComboBox
        if (tipoSelecionado.startsWith("Animais")) {
            lista = listaAnimais;
        } else if (tipoSelecionado.startsWith("Pessoas")) {
            lista = listaPessoas;
        } else if (tipoSelecionado.startsWith("Funcionários")) {
            lista = listaFuncionarios;
        }

        if (lista != null && !lista.isEmpty()) {
            imprimirRelatorio(lista, 0); // Chamada da função recursiva genérica
        } else {
            areaTextoRelatorio.append("Lista vazia ou tipo não reconhecido.");
        }
    }

    // --- Lógica de Recursividade Genérica ---
    /**
     * Função recursiva que imprime o toString() de cada objeto de uma lista genérica.
     */
    private <T> void imprimirRelatorio(List<T> lista, int indice) {
        if (indice >= lista.size()) {
            return; // Caso Base
        }

        // O poder do polimorfismo e toString: funciona para qualquer objeto
        areaTextoRelatorio.append((indice + 1) + ". " + lista.get(indice).toString() + "\n");

        imprimirRelatorio(lista, indice + 1); // Passo Recursivo
    }

    // --- Exportar para Ficheiro TXT ---
    private void exportarRelatorio() {
        String texto = areaTextoRelatorio.getText();
        String tipo = ((String) cmbTipo.getSelectedItem()).split(" ")[0];
        String nomeFicheiro = "relatorio_" + tipo + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFicheiro))) {
            writer.print(texto);
            JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso para " + nomeFicheiro);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Métodos de Teste (Simulação de Dados) ---
    private List<Object> criarListaTesteAnimais() {
        List<Object> lista = new ArrayList<>();
        lista.add(new Cachorro("Buddy", 5));
        lista.add(new Cachorro("Luna", 2));
        return lista;
    }
    private List<Object> criarListaTestePessoas() {
        List<Object> lista = new ArrayList<>();
        lista.add(new Estudante("Maria", 20, "2024001"));
        lista.add(new Estudante("Pedro", 22, "2023055"));
        return lista;
    }
    private List<Object> criarListaTesteFuncionarios() {
        List<Object> lista = new ArrayList<>();
        lista.add(new Gerente("Ana", 4500.00));
        lista.add(new Gerente("Carlos", 5000.00));
        return lista;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RelatorioAutomaticoGUI::new);
    }
}