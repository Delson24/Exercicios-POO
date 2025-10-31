package Exercicio6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BancoGUI extends JFrame {
    private List<ContaBancaria> listaContas = new ArrayList<>();
    private DefaultTableModel modeloTabela;

    private JTextField campoNumero = new JTextField(10);
    private JTextField campoSaldo = new JTextField(10);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"ContaCorrente", "ContaPoupanca"});
    private JLabel labelTotalTaxas = new JLabel("Taxas Totais: R$ 0.00");

    public BancoGUI() {
        super("Sistema Bancário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);

        configurarLayout();
        carregarContas();
        listarContas();
        calcularEExibirTaxas();

        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de Cadastro
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Nova Conta"));
        painelCadastro.add(new JLabel("Número:")); painelCadastro.add(campoNumero);
        painelCadastro.add(new JLabel("Saldo:")); painelCadastro.add(campoSaldo);
        painelCadastro.add(new JLabel("Tipo:")); painelCadastro.add(comboTipo);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarConta());
        painelCadastro.add(btnCadastrar);

        // Tabela
        modeloTabela = new DefaultTableModel(new Object[]{"Número", "Tipo", "Saldo", "Taxa"}, 0);
        JTable tabelaContas = new JTable(modeloTabela);

        // Painel de Botões/Info
        JPanel painelBotoesInfo = new JPanel(new GridLayout(3, 1));

        JButton btnSalvar = new JButton("Gravar Ficheiro");
        JButton btnCalcularTaxas = new JButton("Calcular Taxas Totais (Recursivo)");

        btnSalvar.addActionListener(e -> salvarContas());
        btnCalcularTaxas.addActionListener(e -> calcularEExibirTaxas());

        painelBotoesInfo.add(btnSalvar);
        painelBotoesInfo.add(btnCalcularTaxas);
        painelBotoesInfo.add(labelTotalTaxas);

        add(painelCadastro, BorderLayout.NORTH);
        add(new JScrollPane(tabelaContas), BorderLayout.CENTER);
        add(painelBotoesInfo, BorderLayout.SOUTH);
    }

    private void cadastrarConta() {
        try {
            String numero = campoNumero.getText();
            double saldo = Double.parseDouble(campoSaldo.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            ContaBancaria novaConta = null;
            if ("ContaCorrente".equals(tipo)) {
                novaConta = new ContaCorrente(numero, saldo);
            } else if ("ContaPoupanca".equals(tipo)) {
                novaConta = new ContaPoupanca(numero, saldo);
            }

            if (novaConta != null) {
                listaContas.add(novaConta);
                JOptionPane.showMessageDialog(this, "Conta cadastrada!");
                campoNumero.setText(""); campoSaldo.setText("");
                listarContas();
                calcularEExibirTaxas();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Saldo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarContas() {
        carregarContas();
        modeloTabela.setRowCount(0);
        for (ContaBancaria conta : listaContas) {
            modeloTabela.addRow(new Object[]{
                    conta instanceof ContaCorrente ? ((ContaCorrente)conta).getNumeroConta() : ((ContaPoupanca)conta).getNumeroConta(),
                    conta.getClass().getSimpleName(),
                    String.format("%.2f", conta.getSaldo()),
                    String.format("%.2f", conta.calcularTaxa()) // Polimorfismo
            });
        }
    }

    private void salvarContas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contas.dat"))) {
            oos.writeObject(listaContas);
            JOptionPane.showMessageDialog(this, "Contas salvas com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarContas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contas.dat"))) {
            listaContas = (List<ContaBancaria>) ois.readObject();
        } catch (FileNotFoundException e) {
            listaContas = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularEExibirTaxas() {
        carregarContas(); // Garante dados atualizados
        double total = somarTaxas(listaContas, 0);
        labelTotalTaxas.setText(String.format("Taxas Totais: MZN %.2f", total));
    }

    // --- Lógica Recursiva ---
    /**
     * Soma o valor total das taxas de todas as contas usando recursão.
     */
    private double somarTaxas(List<ContaBancaria> contas, int indice) {
        if (indice >= contas.size()) {
            return 0.0; // Caso Base
        }

        // Polimorfismo: cada conta calcula sua própria taxa
        double taxaAtual = contas.get(indice).calcularTaxa();

        return taxaAtual + somarTaxas(contas, indice + 1); // Passo Recursivo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoGUI::new);
    }
}