package cemisV1_2.model.dao;

import cemisV1_2.model.ExameTipo;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ptol1
 */
public class ExameTipoDAO {

    private static PreparedStatement opNovo;
    private static PreparedStatement opListar;
    private static PreparedStatement opListarSoId;
    private static PreparedStatement opListarPorTipo;
    private static PreparedStatement opListarPorId;

    public ExameTipoDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opNovo = conexao.prepareStatement("INSERT INTO exametipo(tipoExame) VALUES(?)");
            opListar = conexao.prepareStatement("SELECT * FROM exametipo ORDER BY tipoExame");
            opListarSoId = conexao.prepareStatement("SELECT * FROM exametipo ORDER BY idTipoExame");
            opListarPorTipo = conexao.prepareStatement("SELECT * FROM exametipo WHERE tipoExame = ? ORDER BY tipoExame");
            opListarPorId = conexao.prepareStatement("SELECT * FROM exametipo WHERE idTipoExame = ? ORDER BY tipoExame");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ExameTipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean novoTipoExame(ExameTipo tipoExame) {
        try {
            opNovo.clearParameters();
            opNovo.setString(1, tipoExame.getTipoExame());
            opNovo.executeUpdate();
            return true;

        } catch (SQLException ex) {

            return false;
        }
    }

    public List<ExameTipo> listarTodos() throws SQLException {
        List<ExameTipo> listTipoExame = new ArrayList<>();

        ResultSet resultado = opListarSoId.executeQuery();
        while (resultado.next()) {
            ExameTipo tipoExameAtual = new ExameTipo();
            tipoExameAtual.setIdTipoExame(resultado.getInt("idTipoExame"));
            tipoExameAtual.setTipoExame(resultado.getString("tipoExame"));
            listTipoExame.add(tipoExameAtual);
        }
        return listTipoExame;
    }

    public List<ExameTipo> listarTodosId() throws SQLException {
        List<ExameTipo> listTipoExame = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            ExameTipo tipoExameAtual = new ExameTipo();
            tipoExameAtual.setIdTipoExame(resultado.getInt("idTipoExame"));
            tipoExameAtual.setTipoExame(resultado.getString("tipoExame"));
            listTipoExame.add(tipoExameAtual);
        }
        return listTipoExame;
    }

    public int listarPorId(String tipoExame) throws SQLException {
        int id = -1;
        opListarPorTipo.clearParameters();
        opListarPorTipo.setString(1, tipoExame);
        ResultSet resultado = opListarPorTipo.executeQuery();
        if (resultado.next()) {
            id = resultado.getInt("idTipoExame");
        }
        return id;
    }

    public String listarPorTipo(int idTipoExame) throws SQLException {
        String tipo = "";
        opListarPorId.clearParameters();
        opListarPorId.setInt(1, idTipoExame);
        ResultSet resultado = opListarPorId.executeQuery();
        if (resultado.next()) {
            tipo = resultado.getString("tipoExame");
        }
        return tipo;
    }

}
