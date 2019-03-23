package cemisV1_2.model.dao;

import cemisV1_2.model.Exame;
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

public class ExameDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opListarPorId;
    private static PreparedStatement opListarExameTipo;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;

    public ExameDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM exame");
            opListarPorId = conexao.prepareStatement("SELECT * FROM exame WHERE idExame = ?");
            opListarExameTipo = conexao.prepareStatement("SELECT tipoexame.tipoExame FROM exame\n"
                    + "INNER JOIN tipoexame ON exame.TipoExame = tipoexame.idTipoExame\n"
                    + "ORDER BY exame.tipoExame;");
            opNovo = conexao.prepareStatement("INSERT INTO exame(TipoExame, exame) VALUES(?, ?)");
            //  opPesquisar = conexao.prepareStatement("SELECT * FROM exame WHERE TipoExame LIKE ? OR  exame LIKE ?");
            opPesquisar = conexao.prepareStatement("SELECT exame.idExame, "
                    + "exame.exame, tipoexame.tipoExame FROM exame\n"
                    + "INNER JOIN tipoexame ON exame.TipoExame = tipoexame.idTipoExame\n"
                    + "ORDER BY exame.tipoExame "
                    + "WHERE TipoExame LIKE ? OR  exame LIKE ?");
            opAtualizar = conexao.prepareStatement("UPDATE exame SET TipoExame = ?, exame = ? WHERE idExame = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM exame WHERE idExame = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean novoExame(Exame exame) {
        try {
            opNovo.clearParameters();
            opNovo.setInt(1, exame.getTipoexame());
            opNovo.setString(2, exame.getExame());
            opNovo.executeUpdate();
            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Exame> listarTodos() throws Exception {
        List<Exame> exames = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            Exame exameAtual = new Exame();
            exameAtual.setIdExame(resultado.getInt("idExame"));
            exameAtual.setTipoexame(resultado.getInt("TipoExame"));
            exameAtual.setExame(resultado.getString("exame"));
            exames.add(exameAtual);
        }
        return exames;
    }

    public List<Exame> pesquiarExame(String pesquisa) throws Exception {
        try {
            List<Exame> exames = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            opPesquisar.setString(2, pesquisa);
//            opPesquisar.setString(3, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
            while (resultado.next()) {
                Exame exameAtual = new Exame();
                exameAtual.setIdExame(resultado.getInt("idExame"));
                exameAtual.setTipoexame(resultado.getInt("TipoExame"));
                exameAtual.setExame(resultado.getString("exame"));
                exames.add(exameAtual);
            }

            return exames;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse exame não foi encontrado.", ex);
        }
    }

    public Exame listarPorId(Integer idExame) throws Exception {
        Exame exame = new Exame();
        opListarPorId.clearParameters();
        opListarPorId.setInt(1, idExame);
        ResultSet resultado = opListarPorId.executeQuery();
        int i = 0;
        while (resultado.next()) {
            if (i == idExame) {
                exame.setIdExame(resultado.getInt("idExame"));
                exame.setTipoexame(resultado.getInt("TipoExame"));
                exame.setExame(resultado.getString("exame"));
            } else {
                i++;
            }
        }
        return exame;
    }

    public void atualizarExame(Exame exame) throws Exception {
        try {

            opAtualizar.clearParameters();
            opAtualizar.setObject(1, exame.getTipoexame());
            opAtualizar.setString(2, exame.getExame());
            opAtualizar.setInt(3, exame.getIdExame());
            opAtualizar.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception("Parou aqui.");
        }
    }

    public boolean excluirExame(Integer idExame) throws Exception {
        try {
            opExcluir.setInt(1, idExame);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            // alterar para JOptionPane
            return false;
        }
    }
}
