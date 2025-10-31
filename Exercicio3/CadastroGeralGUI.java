import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class CadastroGeralGUI extends JFrame {
    private List<Pessoa> listaCadastro = new ArrayList<>();
    private JTable tabelaCadastro;
    private DefaultTableModel tableModel;

    // ... Componentes de formulário (Nome, Idade, Matrícula opcional, Tipo JComboBox) ...

    public CadastroGeralGUI() {
        // Colunas da JTable para Polimorfismo
        tableModel = new DefaultTableModel(new Object[]{"Nome", "Idade", "Matrícula", "Tipo"}, 0);
        tabelaCadastro = new JTable(tableModel);

        // ... Botões para salvar/carregar ...
        // ... Ação de cadastrar (criar Pessoa ou Aluno e adicionar a listaCadastro) ...
    }

    private void listarCadastro() {
        carregarCadastro();
        tableModel.setRowCount(0); // Limpa

        for (Pessoa p : listaCadastro) {
            String matricula = "";
            String tipo = p.getClass().getSimpleName();

            // Polimorfismo e verificação de tipo (instanceof)
            if (p instanceof Aluno) {
                Aluno a = (Aluno) p;
                matricula = a.getMatricula();
            }

            // Adicionar à JTable
            tableModel.addRow(new Object[]{
                    p.getNome(),
                    p.getIdade(),
                    matricula,
                    tipo
            });

            // O toString() também é usado para verificação:
            System.out.println(p.toString());
        }
    }

    // Métodos para salvar e carregar (ObjectOutputStream/ObjectInputStream)
    private void salvarCadastro() { /* ... */ }
    private void carregarCadastro() { /* ... */ }
}