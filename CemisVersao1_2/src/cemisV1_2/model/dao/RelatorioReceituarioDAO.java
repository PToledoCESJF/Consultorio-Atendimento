package cemisV1_2.model.dao;

import cemisV1_2.model.Receituario;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RelatorioReceituarioDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opUltimo;
    private static PreparedStatement opPesquisarPorId;
    private static PreparedStatement opReceituario;

    public RelatorioReceituarioDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM receituario ORDER BY dataReceituario");
            opUltimo = conexao.prepareStatement("SELECT * FROM receituario ORDER BY idReceituario");
            opPesquisarPorId = conexao.prepareStatement("SELECT * FROM receituario WHERE idReceituario ORDER BY idReceituario");
            opReceituario = conexao.prepareStatement("SELECT receituarioitens.`ordem`, receituarioitens.`quantidade`,"
                    + " receituarioitens.`posologia`, receituarioitens.`dose`, receituarioitens.`TipoUso`, paciente.`nomePaciente`,"
                    + " paciente.`prontuario`, paciente.`endereco`, paciente.`cidade`, medicamento.`medicamento`,"
                    + " receituario.`dataReceituario`, receituarioitens.`Receituario`"
                    + " FROM"
                    + " `receituario` receituario INNER JOIN `receituarioitens` receituarioitens"
                    + " ON receituario.`idReceituario` = receituarioitens.`Receituario`\n"
                    + " INNER JOIN `medicamento` medicamento ON receituarioitens.`Medicamento` = medicamento.`idMedicamento`\n"
                    + " INNER JOIN `paciente` paciente ON receituario.`Paciente` = paciente.`idPaciente`\n"
                    + " WHERE receituario = ? ORDER BY receituarioitens.`TipoUso` DESC, receituarioitens.`ordem` ASC;");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RelatorioReceituarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet relatorioReceituario(int receituario) throws SQLException {
        opReceituario.clearParameters();
        opReceituario.setInt(1, receituario);
        ResultSet resultado = opReceituario.executeQuery();
        return resultado;
    }

    public List<Receituario> listarTodos() throws Exception {
        List<Receituario> receituarios = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            Receituario receituarioAtual = new Receituario();
            receituarioAtual.setIdReceituario(resultado.getInt("idReceituario"));
            receituarioAtual.setPaciente(resultado.getInt("Paciente"));
            receituarioAtual.setDataReceituario(new java.util.Date(resultado.getDate("dataReceituario").getTime()));
            receituarios.add(receituarioAtual);
        }
        return receituarios;
    }

    public Receituario receituarioAtual() throws SQLException {
        Receituario receituarioAtual = new Receituario();
        opUltimo.clearParameters();
        ResultSet resultado = opUltimo.executeQuery();

        if (resultado.last()) {
            receituarioAtual.setIdReceituario(resultado.getInt("idReceituario"));
            receituarioAtual.setPaciente(resultado.getInt("Paciente"));
            receituarioAtual.setDataReceituario(new java.util.Date(resultado.getDate("dataReceituario").getTime()));
        }
        return receituarioAtual;
    }

    public List<Receituario> pesquiarReceituario(String pesquisa) throws Exception {
        try {
            List<Receituario> receituarios = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisarPorId.clearParameters();
            opPesquisarPorId.setString(1, pesquisa);
            opPesquisarPorId.setString(2, pesquisa);
            ResultSet resultado = opPesquisarPorId.executeQuery();
            while (resultado.next()) {
                Receituario receituarioAtual = new Receituario();
                receituarioAtual.setIdReceituario(resultado.getInt("idReceituario"));
                receituarioAtual.setPaciente(resultado.getInt("Paciente"));
                receituarioAtual.setDataReceituario(new java.util.Date(resultado.getDate("dataReceituario").getTime()));
                receituarios.add(receituarioAtual);
            }

            return receituarios;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse receituario não foi encontrado.", ex);
        }
    }

}
