package cemisV1_2.model.dao;

import cemisV1_2.model.Paciente;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRResultSetDataSource;

public class PacienteDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opPesquisarId;
    private static PreparedStatement opPesquisarNome;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;
    private static Connection conexao;

    public PacienteDAO() {
        try {
            conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM paciente");
            opNovo = conexao.prepareStatement("INSERT INTO paciente(nomePaciente, prontuario, endereco, cidade, telefone) VALUES(?, ?, ?, ?, ?)");
            opPesquisar = conexao.prepareStatement("SELECT * FROM paciente WHERE nomePaciente LIKE ? OR  prontuario LIKE ? ORDER BY nomePaciente"); // usar %LIKE%
            opPesquisarId = conexao.prepareStatement("SELECT * FROM paciente WHERE idPaciente = ?");
            opPesquisarNome = conexao.prepareStatement("SELECT * FROM paciente WHERE nomePaciente = ?");
            opAtualizar = conexao.prepareStatement("UPDATE paciente SET nomePaciente = ?, prontuario = ?, endereco = ?, cidade = ?, telefone = ? WHERE idPaciente = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM paciente WHERE idPaciente = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void novoPaciente(Paciente paciente) throws Exception {
        opNovo.clearParameters();
        opNovo.setString(1, paciente.getNomePaciente());
        opNovo.setString(2, paciente.getProntuario());
        opNovo.setString(3, paciente.getEndereco());
        opNovo.setString(4, paciente.getCidade());
        opNovo.setString(5, paciente.getTelefone());
        opNovo.executeUpdate();
    }

    public List<Paciente> listarTodos() throws Exception {
        List<Paciente> pacientes = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            Paciente pacienteAtual = new Paciente();
            pacienteAtual.setIdPaciente(resultado.getInt("idPaciente"));
            pacienteAtual.setNomePaciente(resultado.getString("nomePaciente"));
            pacienteAtual.setProntuario(resultado.getString("prontuario"));
            pacienteAtual.setEndereco(resultado.getString("endereco"));
            pacienteAtual.setCidade(resultado.getString("cidade"));
            pacienteAtual.setTelefone(resultado.getString("telefone"));
            pacientes.add(pacienteAtual);
        }
        return pacientes;
    }

    public ResultSet relatorioPacientes() throws SQLException {
        opListar.clearParameters();
        ResultSet resultado = opListar.executeQuery();
        return resultado;
    }

    public ResultSet relatorioPesquisaPacientes(String pesquisa) throws SQLException {
        opPesquisarNome.clearParameters();
        opPesquisarNome.setString(1, pesquisa);
        ResultSet resultado = opPesquisarNome.executeQuery();
        return resultado;
    }

    public List<Paciente> pesquiarPaciente(String pesquisa) throws Exception {
        try {
            List<Paciente> pacientes = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            opPesquisar.setString(2, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
            while (resultado.next()) {
                Paciente pacienteAtual = new Paciente();
                pacienteAtual.setIdPaciente(resultado.getInt("idPaciente"));
                pacienteAtual.setNomePaciente(resultado.getString("nomePaciente"));
                pacienteAtual.setProntuario(resultado.getString("prontuario"));
                pacienteAtual.setEndereco(resultado.getString("endereco"));
                pacienteAtual.setCidade(resultado.getString("cidade"));
                pacienteAtual.setTelefone(resultado.getString("telefone"));
                pacientes.add(pacienteAtual);
            }

            return pacientes;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse paciente não foi encontrado.", ex);
        }
    }

    public Paciente pesquiarPacienteId(int id) throws Exception {
        try {
            opPesquisarId.clearParameters();
            opPesquisarId.setInt(1, id);
            ResultSet resultado = opPesquisarId.executeQuery();
            if(resultado.next()) {
                Paciente pacienteAtual = new Paciente();
                pacienteAtual.setIdPaciente(resultado.getInt("idPaciente"));
                pacienteAtual.setNomePaciente(resultado.getString("nomePaciente"));
                pacienteAtual.setProntuario(resultado.getString("prontuario"));
                pacienteAtual.setEndereco(resultado.getString("endereco"));
                pacienteAtual.setCidade(resultado.getString("cidade"));
                pacienteAtual.setTelefone(resultado.getString("telefone"));
                return pacienteAtual;
            }
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse paciente não foi encontrado.", ex);
        }
        return null;
    }
    
    public Paciente pesquiarPacienteNome(String nome) throws Exception {
        try {
            opPesquisarNome.clearParameters();
            opPesquisarNome.setString(1, nome);
            ResultSet resultado = opPesquisarNome.executeQuery();
            if(resultado.next()) {
                Paciente pacienteAtual = new Paciente();
                pacienteAtual.setIdPaciente(resultado.getInt("idPaciente"));
                pacienteAtual.setNomePaciente(resultado.getString("nomePaciente"));
                pacienteAtual.setProntuario(resultado.getString("prontuario"));
                pacienteAtual.setEndereco(resultado.getString("endereco"));
                pacienteAtual.setCidade(resultado.getString("cidade"));
                pacienteAtual.setTelefone(resultado.getString("telefone"));
                return pacienteAtual;
            }
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse paciente não foi encontrado.", ex);
        }
        return null;
    }
    
    public void atualizarPaciente(Paciente paciente) throws Exception {
        opAtualizar.clearParameters();
        opAtualizar.setString(1, paciente.getNomePaciente());
        opAtualizar.setString(2, paciente.getProntuario());
        opAtualizar.setString(3, paciente.getEndereco());
        opAtualizar.setString(4, paciente.getCidade());
        opAtualizar.setString(5, paciente.getTelefone());
        opAtualizar.setInt(6, paciente.getIdPaciente());
        opAtualizar.executeUpdate();
    }

    public boolean excluirPaciente(int idPaciente) throws Exception {
        try {
            opExcluir.setInt(1, idPaciente);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
