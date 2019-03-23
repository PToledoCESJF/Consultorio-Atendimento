package cemisV1_2.model.dao;

import cemisV1_2.model.PedidoExameItens;
import cemisV1_2.model.Pedidoexame;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoExameDAO {
    
    private static PreparedStatement opNovoPedExame;
    private static PreparedStatement oplistarPedExame;
    private static PreparedStatement opNovoPedExameItens;
    private static PreparedStatement opUltimoPedExame;
    private static PreparedStatement opRelatorioPedExame;

    public PedidoExameDAO() {
        try {
        Connection conexao = ConnectionFactory.createConnection();
        opNovoPedExame = conexao.prepareStatement("INSERT INTO pedidoexame(Paciente, dataPedidoExame) VALUES(?, ?)");
        oplistarPedExame = conexao.prepareStatement("SELECT * FROM pedidoexame ORDER BY idPedidoExame");
        opUltimoPedExame = conexao.prepareStatement("SELECT * FROM pedidoexame ORDER BY idPedidoExame");
        opNovoPedExameItens = conexao.prepareStatement("INSERT INTO pedidoexameitens(PedidoExame, Exame) VALUES(?, ?)");
        opRelatorioPedExame = conexao.prepareStatement("SELECT pedidoexame.idPedidoExame, paciente.nomePaciente,"
                + " exametipo.tipoExame, exame.exame, pedidoexame.dataPedidoExame"
                + " FROM" 
                + " paciente INNER JOIN pedidoexame ON paciente.idPaciente = pedidoexame.Paciente"
                + " INNER JOIN pedidoexameitens ON pedidoexame.idPedidoExame = pedidoexameitens.PedidoExame"
                + " INNER JOIN exame ON exame.idExame = pedidoexameitens.Exame"
                + " INNER JOIN exametipo ON exame.TipoExame = exametipo.idTipoExame"
                + " WHERE pedidoexame.idPedidoExame = ?"
                + " ORDER BY exametipo.idTipoExame");
        
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PedidoExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void novoPedidoExame(Pedidoexame pe) throws SQLException{
        opNovoPedExame.clearParameters();
        opNovoPedExame.setInt(1, pe.getPaciente());
        opNovoPedExame.setDate(2, new java.sql.Date(pe.getDataPedidoExame().getTime()));
        opNovoPedExame.executeUpdate();
    }
    
    public List<Pedidoexame> listarPedExame() throws SQLException{
        List<Pedidoexame> lstPedExame = new ArrayList<>();
        
        oplistarPedExame.clearParameters();
        ResultSet resultado = oplistarPedExame.executeQuery();
        while(resultado.next()){
            Pedidoexame peAtual = new Pedidoexame();
            peAtual.setIdPedidoExame(resultado.getInt("idPedidoExame"));
            peAtual.setPaciente(resultado.getInt("Paciente"));
            peAtual.setDataPedidoExame(new java.util.Date(resultado.getDate("dataPedidoExame").getTime()));
            lstPedExame.add(peAtual);
        }
        return lstPedExame;
    }
    
    public Pedidoexame utimoPedidoExame() throws SQLException{
        Pedidoexame pe = new Pedidoexame();
        opUltimoPedExame.clearParameters();
        ResultSet resultado = opUltimoPedExame.executeQuery();

        if(resultado.last()){
            pe.setIdPedidoExame(resultado.getInt("idPedidoExame"));
            pe.setPaciente(resultado.getInt("Paciente"));
            pe.setDataPedidoExame(new java.util.Date(resultado.getDate("dataPedidoExame").getTime()));
        }
        return pe;
    }
    
    public void novoPedidoExameItens(List<PedidoExameItens> lpei) throws SQLException{
        for(int i = 0; i < lpei.size(); i++){
            opNovoPedExameItens.clearParameters();
            opNovoPedExameItens.setInt(1, lpei.get(i).getPedidoExame());
            opNovoPedExameItens.setInt(2, lpei.get(i).getExame());
            opNovoPedExameItens.executeUpdate();
        }
            
    }
    
    public ResultSet relatorioPedExame(int idPedExame)throws SQLException{
        opRelatorioPedExame.clearParameters();
        opRelatorioPedExame.setInt(1, idPedExame);
        return opRelatorioPedExame.executeQuery();
    }
    
}
