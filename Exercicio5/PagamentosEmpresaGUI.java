package Exercicio5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentosEmpresaGUI extends JFrame {
    private List<ServicoPagavel> listaDeContratos = new ArrayList<>();
    private DefaultTableModel modeloTabela;

    private JTextField campoDetalhe1 = new JTextField(15);
    private JTextField campoDetalhe2 = new JTextField(10);
    private JTextField campoDetalhe3 = new JTextField(5);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Assalariado (Nome/Salário)", "Prestador (Empresa/Custo/Horas)"});
    private JLabel labelTotal = new JLabel("Total a Pagar: R$ 0.00");

    public PagamentosEmpresaGUI() {
        super("Sistema de Pagamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);

        configurarLayout();
        carregarContratos();
        calcularListarPagamentos();
        setVisible(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de Cadastro (simplificado)
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Novo Contrato"));
        painelCadastro.add(comboTipo);
        painelCadastro.add(campoDetalhe1);
        painelCadastro.add(campoDetalhe2);
        painelCadastro.add(campoDetalhe3);

        JButton btnCadastrar = new JButton("Adicionar Contrato");
        btnCadastrar.addActionListener(e -> cadastrarContrato());
        painelCadastro.add(btnCadastrar);

        // Tabela
        modeloTabela = new DefaultTableModel(new Object[]{"Tipo", "Descrição do Contrato", "Valor Devido (R$)"}, 0);
        JTable tabelaPagamentos = new JTable(modeloTabela);

        // Painel Sul
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton btnSalvar = new JButton("Salvar Contratos");
        JButton btnCalcular = new JButton("Calcular Todos os Pagamentos");

        btnSalvar.addActionListener(e -> salvarContratos());
        btnCalcular.addActionListener(e -> calcularListarPagamentos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCalcular);
        painelBotoes.add(labelTotal);

        add(painelCadastro, BorderLayout.NORTH);
        add(new JScrollPane(tabelaPagamentos), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarContrato() {
        try {
            String tipo = (String) comboTipo.getSelectedItem();
            ServicoPagavel novoContrato = null;

            if (tipo.startsWith("Assalariado")) {
                novoContrato = new Assalariado(campoDetalhe1.getText(), Double.parseDouble(campoDetalhe2.getText()));
            } else if (tipo.startsWith("Prestador")) {
                novoContrato = new PrestadorServico(campoDetalhe1.getText(), Double.parseDouble(campoDetalhe2.getText()), Integer.parseInt(campoDetalhe3.getText()));
            }

            if (novoContrato != null) {
                listaDeContratos.add(novoContrato);
                JOptionPane.showMessageDialog(this, "Contrato adicionado.");
                campoDetalhe1.setText(""); campoDetalhe2.setText(""); campoDetalhe3.setText("");
                calcularListarPagamentos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarContratos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contratos.dat"))) {
            oos.writeObject(listaDeContratos);
            JOptionPane.showMessageDialog(this, "Contratos salvos!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarContratos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contratos.dat"))) {
            listaDeContratos = (List<ServicoPagavel>) ois.readObject();
        } catch (FileNotFoundException e) {
            listaDeContratos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularListarPagamentos() {
        carregarContratos();
        modeloTabela.setRowCount(0);
        double totalGeral = 0.0;

        for (ServicoPagavel contrato : listaDeContratos) {
            double pagamento = contrato.calcularValorDevido(); // POLIMORFISMO DE INTERFACE!
            totalGeral += pagamento;

            modeloTabela.addRow(new Object[]{
                    contrato.getClass().getSimpleName(),
                    contrato.toString(),
                    String.format("%.2f", pagamento)
            });
        }

        labelTotal.setText(String.format("Total a Pagar: R$ %.2f", totalGeral));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PagamentosEmpresaGUI::new);
    }
}