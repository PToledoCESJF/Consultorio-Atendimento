package cemisV1_2.controller;

import cemisV1_2.model.Atestado;
import cemisV1_2.model.AtestadoModelo;
import cemisV1_2.model.dao.AtestadoDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtestadoController {
    
    private static AtestadoDAO atDAO = new AtestadoDAO();
    
    public static boolean salvarModelo(AtestadoModelo am){
        try {
            atDAO.salvarModelo(am);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static List<AtestadoModelo> listarModelos(){
        try {
            List<AtestadoModelo> lam = new ArrayList<>();
            return atDAO.listarModelos();
        } catch (SQLException e) {
            return null;
        }
    }
    
    public static boolean atualizarModelo(AtestadoModelo am){
        try {
            atDAO.atualizarModelo(am);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static boolean excluirModelo(int idModelo){
            try {
                atDAO.excluirModelo(idModelo);
                return true;
            } catch (SQLException e) {
                return false;
            }
    }
    
    public static boolean salvarAtestado(Atestado at){
        try {
            atDAO.salvarAtestado(at);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static List<Atestado> listarAtestado(){
        try {
            return atDAO.listarAtestados();
        } catch (SQLException e) {
            return null;
        }
    }
    
    public static boolean atualizarAtestado(Atestado at){
        try {
            atDAO.atualizarAtestado(at);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static boolean excluirAtestado(int idAtestado){
            try {
                atDAO.excluirAtestado(idAtestado);
                return true;
            } catch (SQLException e) {
                return false;
            }
    }
    
    public static ResultSet relatorioAtestado(int atestado) throws SQLException{
        return atDAO.relatorioAtestado(atestado);
    }
    
    public static int utimoSalvo() throws SQLException{
        return atDAO.idAt();
    }
    
}
