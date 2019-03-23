package cemisV1_2.model.dao;

import cemisV1_2.model.Medicamento;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicamentoDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opListarPorMedicamento;
    private static PreparedStatement opListarPorId;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;

    public MedicamentoDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM medicamento ORDER BY principioAtivo");
            opListarPorMedicamento = conexao.prepareStatement("SELECT * FROM medicamento WHERE medicamento = ?");
            opListarPorId = conexao.prepareStatement("SELECT * FROM medicamento WHERE idMedicamento = ?");
            opNovo = conexao.prepareStatement("INSERT INTO medicamento(principioAtivo, medicamento, laboratorio, apresentacao) VALUES(?, ?, ?, ?)");
            opPesquisar = conexao.prepareStatement("SELECT * FROM medicamento WHERE principioAtivo LIKE ? OR  medicamento LIKE ? ORDER BY principioAtivo");
            opAtualizar = conexao.prepareStatement("UPDATE medicamento SET principioAtivo = ?, medicamento = ?, laboratorio = ?, apresentacao = ? WHERE idMedicamento = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM medicamento WHERE idMedicamento = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MedicamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean novoMedicamento(Medicamento medicamento) {
        try {

            opNovo.clearParameters();
            opNovo.setString(1, medicamento.getPrincipioAtivo());
            opNovo.setString(2, medicamento.getMedicamento());
            opNovo.setString(3, medicamento.getLaboratorio());
            opNovo.setString(4, medicamento.getApresentacao());
            opNovo.executeUpdate();
            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Medicamento> listarTodos() throws Exception {
        List<Medicamento> medicamentos = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            Medicamento medicamentoAtual = new Medicamento();
            medicamentoAtual.setIdMedicamento(resultado.getInt("idMedicamento"));
            medicamentoAtual.setPrincipioAtivo(resultado.getString("principioAtivo"));
            medicamentoAtual.setMedicamento(resultado.getString("medicamento"));
            medicamentoAtual.setLaboratorio(resultado.getString("laboratorio"));
            medicamentoAtual.setApresentacao(resultado.getString("apresentacao"));
            medicamentos.add(medicamentoAtual);
        }
        return medicamentos;
    }

    public int listarPorId(String medicamento) throws SQLException {
        int id = -1;
        opListarPorMedicamento.clearParameters();
        opListarPorMedicamento.setString(1, medicamento);
        ResultSet resultado = opListarPorMedicamento.executeQuery();
        if (resultado.next()) {
            id = resultado.getInt("idMedicamento");
        }
        return id;
    }

    public String listarPorMedicamento(int idMedicamento) throws SQLException {
        String medicamento = "";
        opListarPorId.clearParameters();
        opListarPorId.setInt(1, idMedicamento);
        ResultSet resultado = opListarPorId.executeQuery();
        if (resultado.next()) {
            medicamento = resultado.getString("medicamento");
        }
        return medicamento;
    }

    public List<Medicamento> pesquiarMedicamento(String pesquisa) throws Exception {
        try {
            List<Medicamento> medicamentos = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            opPesquisar.setString(2, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
            while (resultado.next()) {
                Medicamento medicamentoAtual = new Medicamento();
                medicamentoAtual.setIdMedicamento(resultado.getInt("idMedicamento"));
                medicamentoAtual.setPrincipioAtivo(resultado.getString("principioAtivo"));
                medicamentoAtual.setMedicamento(resultado.getString("medicamento"));
                medicamentoAtual.setLaboratorio(resultado.getString("laboratorio"));
                medicamentoAtual.setApresentacao(resultado.getString("apresentacao"));
                medicamentos.add(medicamentoAtual);
            }

            return medicamentos;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse medicamento não foi encontrado.", ex);
        }
    }

    public void atualizarMedicamento(Medicamento medicamento) throws Exception {
        try {

            opAtualizar.clearParameters();
            opAtualizar.setString(1, medicamento.getPrincipioAtivo());
            opAtualizar.setString(2, medicamento.getMedicamento());
            opAtualizar.setString(3, medicamento.getLaboratorio());
            opAtualizar.setString(4, medicamento.getApresentacao());
            opAtualizar.setInt(5, medicamento.getIdMedicamento());
            opAtualizar.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception("Parou aqui.");
        }
    }

    public boolean excluirMedicamento(int idMedicamento) throws Exception {
        try {
            opExcluir.setInt(1, idMedicamento);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
