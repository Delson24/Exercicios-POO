package Exercicio4;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaFolhaPagamento extends JFrame {
    private List<Colaborador> listaEquipa = new ArrayList<>();
    private JLabel labelTotal = new JLabel("Folha Total: R$ 0.00");

    private JTextField campoNome = new JTextField(15);
    private JTextField campoSalario = new JTextField(10);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Colaborador", "Gerente"});

    public SistemaFolhaPagamento() {
        super("Sistema de Folha de Pagamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);

        configurarLayout();
        carregarEquipaDoFicheiro();
        calcularEExibirTotal();

        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro"));
        painelCadastro.add(new JLabel("Nome:")); painelCadastro.add(campoNome);
        painelCadastro.add(new JLabel("Salário:")); painelCadastro.add(campoSalario);
        painelCadastro.add(new JLabel("Tipo:")); painelCadastro.add(comboTipo);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarColaborador());
        painelCadastro.add(btnCadastrar);

        JPanel painelInfo = new JPanel(new GridLayout(2, 1));

        JButton btnCalcular = new JButton("Calcular Folha Total (Recursivo)");
        btnCalcular.addActionListener(e -> calcularEExibirTotal());

        painelInfo.add(btnCalcular);
        painelInfo.add(labelTotal);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnSalvar = new JButton("Salvar Dados");
        btnSalvar.addActionListener(e -> salvarEquipa());
        painelBotoes.add(btnSalvar);

        add(painelCadastro, BorderLayout.NORTH);
        add(painelInfo, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarColaborador() {
        try {
            String nome = campoNome.getText();
            double salario = Double.parseDouble(campoSalario.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            Colaborador novo = null;
            if ("Gerente".equals(tipo)) {
                novo = new Gerente(nome, salario);
            } else {
                novo = new Colaborador(nome, salario);
            }

            listaEquipa.add(novo);
            JOptionPane.showMessageDialog(this, "Colaborador inserido!");
            campoNome.setText(""); campoSalario.setText("");
            calcularEExibirTotal();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarEquipa() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("equipa.dat"))) {
            oos.writeObject(listaEquipa);
            JOptionPane.showMessageDialog(this, "Equipa salva com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarEquipaDoFicheiro() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("equipa.dat"))) {
            listaEquipa = (List<Colaborador>) ois.readObject();
        } catch (FileNotFoundException e) {
            listaEquipa = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularEExibirTotal() {
        carregarEquipaDoFicheiro();
        double total = somarSalarios(listaEquipa, 0);
        labelTotal.setText(String.format("Folha Total: R$ %.2f", total));
    }

    // --- Lógica Recursiva ---
    /**
     * Soma o salário de todos os colaboradores na lista usando recursão.
     */
    private double somarSalarios(List<Colaborador> colaboradores, int indice) {
        if (indice >= colaboradores.size()) {
            return 0.0; // Caso Base
        }

        // Obtém o salário (funciona para Colaborador e Gerente)
        double salario = colaboradores.get(indice).getSalarioAtual();

        // Retorna o salário atual + a soma do restante (Passo Recursivo)
        return salario + somarSalarios(colaboradores, indice + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaFolhaPagamento::new);
    }
}