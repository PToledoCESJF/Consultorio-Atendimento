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

public class ReceituarioDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opUltimo;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opPesquisarId;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;

    public ReceituarioDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM receituario ORDER BY idReceituario");
            opUltimo = conexao.prepareStatement("SELECT * FROM receituario ORDER BY idReceituario");
            opNovo = conexao.prepareStatement("INSERT INTO receituario(Paciente, dataReceituario) VALUES(?, ?)");
            opPesquisar = conexao.prepareStatement("SELECT * FROM receituario WHERE Paciente LIKE ? ORDER BY dataReceituario");
            opPesquisarId = conexao.prepareStatement("SELECT * FROM receituario WHERE Paciente = ? ORDER BY idReceituario");
            opAtualizar = conexao.prepareStatement("UPDATE receituario SET Paciente = ?, dataReceituario = ? WHERE idReceituario = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM receituario WHERE idReceituario = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReceituarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean novoReceituario(Receituario receituario) {
        try {
            opNovo.clearParameters();
            opNovo.setInt(1, receituario.getPaciente());
            opNovo.setDate(2, new java.sql.Date(receituario.getDataReceituario().getTime()));
            opNovo.executeUpdate();
            return true;

        } catch (SQLException ex) {
            return false;
        }
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
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
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

    public List<Receituario> pesquiarReceituarioId(int pesquisa) throws Exception {
        try {
            List<Receituario> receituarios = new ArrayList<>();
            opPesquisarId.clearParameters();
            opPesquisarId.setInt(1, pesquisa);
            ResultSet resultado = opPesquisarId.executeQuery();
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

    public void atualizarReceituario(Receituario receituario) throws Exception {
        try {

            opAtualizar.clearParameters();
            opAtualizar.setInt(1, receituario.getPaciente());
            opAtualizar.setDate(2, new java.sql.Date(receituario.getDataReceituario().getTime()));
            opAtualizar.setInt(3, receituario.getIdReceituario());
            opAtualizar.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception("Parou aqui.");
        }
    }

    public boolean excluirReceituario(int idReceituario) throws Exception {
        try {
            opExcluir.setInt(1, idReceituario);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
