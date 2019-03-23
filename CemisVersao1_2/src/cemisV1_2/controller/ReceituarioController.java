package cemisV1_2.controller;

import cemisV1_2.model.ReceituarioItens;
import cemisV1_2.model.Receituario;
import cemisV1_2.model.dao.ReceituarioDAO;
import cemisV1_2.model.dao.ReceituarioItensDAO;
import cemisV1_2.model.dao.RelatorioReceituarioDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceituarioController {

    private List<Receituario> listReceituarios = new ArrayList<>();
    private ReceituarioDAO receituarioDAO = new ReceituarioDAO();
    private ReceituarioItensDAO itensReceituarioDAO = new ReceituarioItensDAO();
    private RelatorioReceituarioDAO relatorioReceiturarioDAO = new RelatorioReceituarioDAO();

    public ReceituarioController() {

    }

    public List<Receituario> listarReceituario() {
        try {
            return listReceituarios = receituarioDAO.listarTodos();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Receituario> pesquisarReceituarioPorPaciente(String pesquisa) throws Exception {        
        return listReceituarios = receituarioDAO.pesquiarReceituario(pesquisa);
    }

    public List<Receituario> pesquisarReceituarioId(int pesquisa) throws Exception {
        return listReceituarios = receituarioDAO.pesquiarReceituarioId(pesquisa);
    }

    public boolean excluirReceituario(int idReceituario) throws Exception {
        if (idReceituario > 0) {
            return receituarioDAO.excluirReceituario(idReceituario);
        }
        return false;
    }

    public boolean salvarReceituario(Receituario receituario) {
        try {
            receituarioDAO.novoReceituario(receituario);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Receituario receituarioAtual() {
        try {
            return receituarioDAO.receituarioAtual();
        } catch (SQLException ex) {
            return null;
        }
    }

    public void salvarItemReceituario(ReceituarioItens itensReceituario) {
        itensReceituarioDAO.novoItensReceituario(itensReceituario);
    }
    
    public ResultSet relatorioReceituario(int pesquisa) throws SQLException {
        return relatorioReceiturarioDAO.relatorioReceituario(pesquisa);
    }

    public List<Receituario> getListReceituarios() {
        return listReceituarios;
    }

    public void setListReceituarios(List<Receituario> listReceituarios) {
        this.listReceituarios = listReceituarios;
    }

}
