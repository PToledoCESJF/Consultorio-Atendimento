package cemisV1_2.model.dao;

import cemisV1_2.model.ReceituarioItens;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceituarioItensDAO {

    private static PreparedStatement opListar;
    private static PreparedStatement opNovo;
    private static PreparedStatement opPesquisar;
    private static PreparedStatement opAtualizar;
    private static PreparedStatement opExcluir;

    public ReceituarioItensDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opListar = conexao.prepareStatement("SELECT * FROM receituarioitens ORDER BY Receituario");
            opNovo = conexao.prepareStatement("INSERT INTO receituarioitens(Receituario, ordem, Medicamento, "
                    + "quantidade, posologia, dose, tipoUso) VALUES(?, ?, ?, ?, ?, ?, ?)");
            opPesquisar = conexao.prepareStatement("SELECT * FROM receituarioitens WHERE Receituario LIKE ? ORDER BY Receituario");
            opAtualizar = conexao.prepareStatement("UPDATE receituarioitens SET Receituario = ?, ordem = ?, Medicamento = ?, "
                    + "quantidade = ?, posologia = ?, dose = ?, tipoUso = ? WHERE idItensReceituario = ?");
            opExcluir = conexao.prepareStatement("DELETE FROM receituarioitens WHERE idItensReceituario = ?");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReceituarioItensDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean novoItensReceituario(ReceituarioItens itensReceituario) {
        try {
            opNovo.clearParameters();
            opNovo.setInt(1, itensReceituario.getReceituario());
            opNovo.setInt(2, itensReceituario.getOrdem());
            opNovo.setInt(3, itensReceituario.getMedicamento());
            opNovo.setString(4, itensReceituario.getQuantidade());
            opNovo.setString(5, itensReceituario.getPosologia());
            opNovo.setString(6, itensReceituario.getDose());
            opNovo.setString(7, itensReceituario.getTipoUso());
            opNovo.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<ReceituarioItens> listarTodos() throws Exception {
        List<ReceituarioItens> itensReceituarios = new ArrayList<>();

        ResultSet resultado = opListar.executeQuery();
        while (resultado.next()) {
            ReceituarioItens itensReceituarioAtual = new ReceituarioItens();
            itensReceituarioAtual.setIdItensReceituario(resultado.getInt("idItensReceituario"));
            itensReceituarioAtual.setReceituario(resultado.getInt("Receituario"));
            itensReceituarioAtual.setOrdem(resultado.getInt("ordem"));
            itensReceituarioAtual.setMedicamento(resultado.getInt("Medicamento"));
            itensReceituarioAtual.setQuantidade(resultado.getString("quantidade"));
            itensReceituarioAtual.setPosologia(resultado.getString("posologia"));
            itensReceituarioAtual.setDose(resultado.getString("dose"));
            itensReceituarioAtual.setTipoUso(resultado.getString("TipoUso"));
            itensReceituarios.add(itensReceituarioAtual);
        }
        return itensReceituarios;
    }

    public List<ReceituarioItens> pesquiarItensReceituario(String pesquisa) throws Exception {
        try {
            List<ReceituarioItens> itensReceituarios = new ArrayList<>();
            pesquisa = "%" + pesquisa + "%";
            opPesquisar.clearParameters();
            opPesquisar.setString(1, pesquisa);
            ResultSet resultado = opPesquisar.executeQuery();
            while (resultado.next()) {
                ReceituarioItens itensReceituarioAtual = new ReceituarioItens();
                itensReceituarioAtual.setIdItensReceituario(resultado.getInt("idItensReceituario"));
                itensReceituarioAtual.setReceituario(resultado.getInt("Receituario"));
                itensReceituarioAtual.setOrdem(resultado.getInt("ordem"));
                itensReceituarioAtual.setMedicamento(resultado.getInt("Medicamento"));
                itensReceituarioAtual.setQuantidade(resultado.getString("quantidade"));
                itensReceituarioAtual.setPosologia(resultado.getString("posologia"));
                itensReceituarioAtual.setDose(resultado.getString("dose"));
                itensReceituarioAtual.setTipoUso(resultado.getString("TipoUso"));
                itensReceituarios.add(itensReceituarioAtual);
            }

            return itensReceituarios;
        } catch (SQLException ex) {
            //alterar para JOptionPane
            throw new Exception("Esse itensReceituario não foi encontrado.", ex);
        }
    }

    public void atualizarItensReceituario(ReceituarioItens itensReceituario) throws Exception {
        try {

            opAtualizar.clearParameters();
            opAtualizar.setInt(1, itensReceituario.getReceituario());
            opAtualizar.setInt(2, itensReceituario.getOrdem());
            opAtualizar.setInt(3, itensReceituario.getMedicamento());
            opAtualizar.setString(4, itensReceituario.getQuantidade());
            opAtualizar.setString(5, itensReceituario.getPosologia());
            opAtualizar.setString(6, itensReceituario.getDose());
            opAtualizar.setString(7, itensReceituario.getTipoUso());
            opAtualizar.setInt(8, itensReceituario.getIdItensReceituario());
            opAtualizar.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception("Parou aqui.");
        }
    }

    public boolean excluirItensReceituario(int idItensReceituario) throws Exception {
        try {
            opExcluir.setInt(1, idItensReceituario);
            opExcluir.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
