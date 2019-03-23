
package cemisV1_2.controller;

import cemisV1_2.model.PedidoExameItens;
import cemisV1_2.model.Pedidoexame;
import cemisV1_2.model.dao.PedidoExameDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PedidoExameController {
    
    private PedidoExameDAO pedidoExameDAO = new PedidoExameDAO();
    
    public boolean salvarPedExame(Pedidoexame pe){
        try {
            pedidoExameDAO.novoPedidoExame(pe);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar PedidoExame no Banco de Dados: "+e);
            return false;
        }
    }
    
    public List<Pedidoexame> listarPedExame(){
        try {
            return pedidoExameDAO.listarPedExame();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Pedidoexame ultimoPedExame(){
        try {
            return pedidoExameDAO.utimoPedidoExame();
        } catch (SQLException e) {
            System.out.println("Erro ao Buscar o Ultimo no Banco de Dados: "+e);
            return null;
        }
    }
    
    public boolean salvarPedExameItens(List<PedidoExameItens> lpei){
        try {
            pedidoExameDAO.novoPedidoExameItens(lpei);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar ItensPedidoExame no Banco de Dados: "+e);
            return false;
        }
    }
    
    public ResultSet  relatorioPedExame(int idPedExame){
        try {
            return pedidoExameDAO.relatorioPedExame(idPedExame);
        } catch (SQLException e) {
            System.out.println("Erro ao Chamar Relatório PedidoExame no Banco de Dados: "+e);
            return null;
        }
    }
    
}
