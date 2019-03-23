package cemisV1_2.model.dao;

import cemisV1_2.model.Atendimento;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtendimentoDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;

    public AtendimentoDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM atendimento ORDER BY idAtendimento");
            opNovo = conexao.prepareStatement("INSERT INTO atendimento(Agenda, Medico, Paciente, data) VALUES(?, ?, ?, ?)");
            opPesquisar = conexao.prepareStatement("SELECT * FROM atendimento WHERE Paciente LIKE ? OR  data LIKE ? ORDER BY idAtendimento");
            opAtualizar = conexao.prepareStatement("UPDATE atendimento SET Agenda = ?, Medico = ?, Paciente = ?, data = ? WHERE idAtendimento = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM atendimento WHERE idAtendimento = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean novoAtendimento(Atendimento atendimento) {
        try {

            opNovo.clearParameters();
            opNovo.setInt(1, atendimento.getAgenda());
            opNovo.setInt(2, atendimento.getMedico());
            opNovo.setInt(3, atendimento.getPaciente());
            opNovo.setDate(4, new java.sql.Date(atendimento.getDataAtendimento().getTime()));
            opNovo.executeUpdate();
            return true;

        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "não salvo atendimento");
            return false;
        }
    }

    public List<Atendimento> listarTodos() throws Exception {
        List<Atendimento> atendimentos = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            Atendimento atendimentoAtual = new Atendimento();
            atendimentoAtual.setIdAtendimento(resultado.getInt("idAtendimento"));
            atendimentoAtual.setAgenda(resultado.getInt("Agenda"));
            atendimentoAtual.setMedico(resultado.getInt("Medico"));
            atendimentoAtual.setPaciente(resultado.getInt("Paciente"));
            atendimentoAtual.setDataAtendimento(new java.util.Date(resultado.getDate("data").getTime()));
            atendimentos.add(atendimentoAtual);
        }
        return atendimentos;
    }

    public Atendimento atendimentoAtual() throws SQLException {
        Atendimento atendimentoAtual = new Atendimento();
        ResultSet resultado = opListar.executeQuery();

        if (resultado.last()) {
            atendimentoAtual.setIdAtendimento(resultado.getInt("idAtendimento"));
            atendimentoAtual.setAgenda(resultado.getInt("Agenda"));
            atendimentoAtual.setMedico(resultado.getInt("Medico"));
            atendimentoAtual.setPaciente(resultado.getInt("Paciente"));
            atendimentoAtual.setDataAtendimento(new java.util.Date(resultado.getDate("data").getTime()));
        }
        return atendimentoAtual;
    }

    public List<Atendimento> pesquiarAtendimento(String pesquisa) throws Exception {
        try {
            List<Atendimento> atendimentos = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            opPesquisar.setString(2, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
            while (resultado.next()) {
                Atendimento atendimentoAtual = new Atendimento();
                atendimentoAtual.setIdAtendimento(resultado.getInt("idAtendimento"));
                atendimentoAtual.setAgenda(resultado.getInt("Agenda"));
                atendimentoAtual.setMedico(resultado.getInt("Medico"));
                atendimentoAtual.setPaciente(resultado.getInt("Paciente"));
                atendimentoAtual.setDataAtendimento(new java.util.Date(resultado.getDate("data").getTime()));
                atendimentos.add(atendimentoAtual);
            }

            return atendimentos;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse atendimento não foi encontrado.", ex);
        }
    }

    public void atualizarAtendimento(Atendimento atendimento) throws Exception {
        try {

            opAtualizar.clearParameters();
            opAtualizar.setInt(1, atendimento.getAgenda());
            opAtualizar.setInt(2, atendimento.getMedico());
            opAtualizar.setInt(3, atendimento.getPaciente());
            opAtualizar.setDate(4, new java.sql.Date(atendimento.getDataAtendimento().getTime()));
            opAtualizar.setInt(5, atendimento.getIdAtendimento());
            opAtualizar.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception("Parou aqui.");
        }
    }

    public boolean excluirAtendimento(int idAtendimento) throws Exception {
        try {
            opExcluir.setInt(1, idAtendimento);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
