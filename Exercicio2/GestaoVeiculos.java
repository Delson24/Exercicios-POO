package Exercicio2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestaoVeiculos extends JFrame {
    private List<MeioDeTransporte> frotaDeVeiculos = new ArrayList<>();
    private JTextArea areaTextoMovimentos = new JTextArea(10, 30);

    private JTextField campoMarca = new JTextField(15);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Bicicleta"});

    public GestaoVeiculos() {
        super("Gestão de Frota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        configurarLayout();
        carregarFrota();
        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel Norte
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Novo Veículo"));
        painelCadastro.add(new JLabel("Marca:")); painelCadastro.add(campoMarca);
        painelCadastro.add(new JLabel("Tipo:")); painelCadastro.add(comboTipo);
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarVeiculo());
        painelCadastro.add(btnCadastrar);

        // Painel Centro
        areaTextoMovimentos.setEditable(false);
        JScrollPane scrollArea = new JScrollPane(areaTextoMovimentos);

        // Painel Sul
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton botaoSalvar = new JButton("Salvar Frota");
        JButton botaoMovimentos = new JButton("Mostrar Todos Movimentos (Recursivo)");

        botaoSalvar.addActionListener(e -> salvarFrota());
        botaoMovimentos.addActionListener(e -> exibirTodosOsMovimentos());

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoMovimentos);

        add(painelCadastro, BorderLayout.NORTH);
        add(scrollArea, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarVeiculo() {
        String marca = campoMarca.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        MeioDeTransporte novoVeiculo = null;

        if ("Carro".equals(tipo)) {
            novoVeiculo = new Carro(marca);
        } else if ("Bicicleta".equals(tipo)) {
            novoVeiculo = new Bicicleta(marca);
        }

        if (novoVeiculo != null) {
            frotaDeVeiculos.add(novoVeiculo);
            JOptionPane.showMessageDialog(this, "Veículo cadastrado: " + novoVeiculo.toString());
            campoMarca.setText("");
        }
    }

    private void salvarFrota() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("frota.dat"))) {
            oos.writeObject(frotaDeVeiculos);
            JOptionPane.showMessageDialog(this, "Frota salva com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarFrota() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("frota.dat"))) {
            frotaDeVeiculos = (List<MeioDeTransporte>) ois.readObject();
        } catch (FileNotFoundException e) {
            frotaDeVeiculos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Lógica Recursiva ---
    private void exibirTodosOsMovimentos() {
        carregarFrota();
        areaTextoMovimentos.setText("--- Ações de Movimento ---\n");
        if (frotaDeVeiculos.isEmpty()) {
            areaTextoMovimentos.append("Nenhum veículo registado.");
            return;
        }

        imprimirMovimentos(frotaDeVeiculos, 0);
    }

    /**
     * Percorre a lista de veículos e imprime o movimento de cada um na JTextArea usando recursão.
     */
    private void imprimirMovimentos(List<MeioDeTransporte> veiculos, int indice) {
        if (indice >= veiculos.size()) {
            return; // Caso Base
        }

        MeioDeTransporte veiculoAtual = veiculos.get(indice);
        // Polimorfismo: cada objeto sabe como se descrever
        areaTextoMovimentos.append(veiculoAtual.toString() + ": " +
                veiculoAtual.descreverMovimento() + "\n");

        imprimirMovimentos(veiculos, indice + 1); // Chamada recursiva
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestaoVeiculos::new);
    }
}