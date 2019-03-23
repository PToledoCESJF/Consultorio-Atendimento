package cemisV1_2.controller;

import cemisV1_2.model.Exame;
import cemisV1_2.model.ExameTipo;
import cemisV1_2.model.dao.ExameDAO;
import cemisV1_2.model.dao.ExameTipoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExameController {

    private List<Exame> listExames = new ArrayList<>();
    private ExameDAO exameDAO = new ExameDAO();
    private Exame exame = new Exame();

    private List<ExameTipo> listTipoExame = new ArrayList<>();
    private ExameTipoDAO tipoExameDAO = new ExameTipoDAO();
    private ExameTipo tipoExame = new ExameTipo();

    private int opcao = 0;

    public ExameController() {

    }

    public List<Exame> listarExames() {

        try {
            return listExames = exameDAO.listarTodos();
        } catch (Exception ex) {
            return null;
        }

    }

    public boolean salvarTipoExame(String tExame) {
        if (!tExame.equals("")) {
            tipoExame.setTipoExame(tExame);
        }
        return tipoExameDAO.novoTipoExame(tipoExame);
    }

    public List<ExameTipo> listarTipoExame() throws Exception {
        try {
            return listTipoExame = tipoExameDAO.listarTodosId();
        } catch (SQLException ex) {
            return null;
        }

    }

    public int getIdTipoExameByDesc(String tipoExame) {
        int id = -1;
        try {
            id = tipoExameDAO.listarPorId(tipoExame);
        } catch (SQLException ex) {
            Logger.getLogger(ExameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getTipoExameById(int idTipoExame) {
        String tipo = "";
        try {
            tipo = tipoExameDAO.listarPorTipo(idTipoExame);
        } catch (SQLException ex) {
            Logger.getLogger(ExameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipo;
    }

    public boolean excluirExame(int idExame) throws Exception {
        if (idExame > 0) {
            return exameDAO.excluirExame(idExame);
        }
        return false;
    }

    public int salvarExame(Exame exame, boolean botaoClicado) {

        if (validarDados(exame.getTipoexame(), exame.getExame())) {

            this.exame.setExame(exame.getExame());
            this.exame.setTipoexame(exame.getTipoexame());

            if (botaoClicado) {
                try {
                    exameDAO.novoExame(this.exame);
                    opcao = 1;
                } catch (Exception ex) {
                    opcao = 2;
                }
            } else {
                try {
                    this.exame.setIdExame(exame.getIdExame());
                    exameDAO.atualizarExame(this.exame);
                    opcao = 3;

                } catch (Exception ex) {
                    opcao = 4;
                }
            }
        } else {
            opcao = 5;
        }
        return opcao;
    }

    private boolean validarDados(int tipoExame, String exame) {
        return !(tipoExame <= 0 || exame.equals(""));
    }

    public List<Exame> getListExames() {
        return listExames;
    }

    public void setListExames(List<Exame> listExames) {
        this.listExames = listExames;
    }
}
