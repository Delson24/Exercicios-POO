package Exercicio1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioAnimais extends JFrame {
    private List<EspecieAnimal> listaDeAnimais = new ArrayList<>();
    private DefaultTableModel modeloTabela;

    private JTextField campoNome = new JTextField(15);
    private JTextField campoIdade = new JTextField(5);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Cachorro", "Gato"});

    public FormularioAnimais() {
        super("Cadastro de Animais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);

        configurarLayout();
        carregarAnimaisDoFicheiro();
        listarAnimais();

        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de Cadastro (Norte)
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Novo Animal"));
        painelCadastro.add(new JLabel("Nome:")); painelCadastro.add(campoNome);
        painelCadastro.add(new JLabel("Idade:")); painelCadastro.add(campoIdade);
        painelCadastro.add(new JLabel("Tipo:")); painelCadastro.add(comboTipo);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarNovoAnimal());
        painelCadastro.add(btnCadastrar);

        // Tabela (Centro)
        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Idade", "Tipo", "Som"}, 0);
        JTable tabelaAnimais = new JTable(modeloTabela);

        // Painel de Botões (Sul)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton btnSalvar = new JButton("1. Gravar Ficheiro");
        JButton btnListar = new JButton("2. Listar na Tabela");
        JButton btnSons = new JButton("3. Sons (Recursivo)");

        btnSalvar.addActionListener(e -> salvarAnimaisNoFicheiro());
        btnListar.addActionListener(e -> listarAnimais());
        btnSons.addActionListener(e -> mostrarSonsRecursivamente());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnSons);

        add(painelCadastro, BorderLayout.NORTH);
        add(new JScrollPane(tabelaAnimais), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarNovoAnimal() {
        try {
            String nome = campoNome.getText();
            int idade = Integer.parseInt(campoIdade.getText());
            String tipo = (String) comboTipo.getSelectedItem();
            EspecieAnimal animalNovo = null;

            if ("Cachorro".equals(tipo)) {
                animalNovo = new Cachorro(nome, idade);
            } else if ("Gato".equals(tipo)) {
                animalNovo = new Gato(nome, idade);
            }

            if (animalNovo != null) {
                listaDeAnimais.add(animalNovo);
                JOptionPane.showMessageDialog(this, "Animal inserido! Grave para persistir.");
                campoNome.setText(""); campoIdade.setText("");
                listarAnimais(); // Atualiza a tabela após a inserção
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarAnimais() {
        carregarAnimaisDoFicheiro(); // Carrega lista do ficheiro antes de listar
        modeloTabela.setRowCount(0);
        for (EspecieAnimal animal : listaDeAnimais) {
            modeloTabela.addRow(new Object[]{
                    animal.getNome(),
                    animal.getIdade(),
                    animal.getClass().getSimpleName(),
                    animal.emitirSom() // Polimorfismo
            });
        }
    }

    private void salvarAnimaisNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("animais.dat"))) {
            oos.writeObject(listaDeAnimais);
            JOptionPane.showMessageDialog(this, "Dados gravados com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gravar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarAnimaisDoFicheiro() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("animais.dat"))) {
            listaDeAnimais = (List<EspecieAnimal>) ois.readObject();
        } catch (FileNotFoundException e) {
            listaDeAnimais = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Lógica Recursiva ---
    private void mostrarSonsRecursivamente() {
        if (listaDeAnimais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum animal na lista.");
            return;
        }

        String sons = obterSons(listaDeAnimais, 0);
        JOptionPane.showMessageDialog(this, "--- Sons da Quinta ---\n" + sons, "Sons", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Função recursiva que percorre a lista de animais para obter o som de cada um.
     */
    private String obterSons(List<EspecieAnimal> animais, int indice) {
        if (indice >= animais.size()) {
            return ""; // Caso Base
        }

        EspecieAnimal animalAtual = animais.get(indice);
        String somAtual = animalAtual.getNome() + " diz: " + animalAtual.emitirSom() + "\n";

        return somAtual + obterSons(animais, indice + 1); // Passo Recursivo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormularioAnimais::new);
    }
}