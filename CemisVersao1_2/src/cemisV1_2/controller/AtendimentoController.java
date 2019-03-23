package cemisV1_2.controller;

import cemisV1_2.model.Atendimento;
import cemisV1_2.model.dao.AtendimentoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AtendimentoController {

    private List<Atendimento> listAtendimentos = new ArrayList<>();
    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private Atendimento atendimento = new Atendimento();

    private int opcao;

    public AtendimentoController() {

    }

    public List<Atendimento> listarAtendimento() {

        try {
            return listAtendimentos = atendimentoDAO.listarTodos();
        } catch (Exception ex) {
            return null;
        }
    }

    public Atendimento atendimentoAtual() {
        try {
            return atendimentoDAO.atendimentoAtual();
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Atendimento> pesquisarAtendimento(String pesquisa) throws Exception {
        return listAtendimentos = atendimentoDAO.pesquiarAtendimento(pesquisa);
    }

    public boolean inserirAtendimento(Atendimento atendimento) {
        try {
            atendimentoDAO.novoAtendimento(atendimento);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean excluirAtendimento(int idAtendimento) throws Exception {
        if (idAtendimento > 0) {
            return atendimentoDAO.excluirAtendimento(idAtendimento);
        }
        return false;
    }

    public void salvarAtendimento(Atendimento atendimento) {
//        if(atendimento.getPaciente() != null && !atendimento.getData().equals("")){
        try {
            atendimentoDAO.novoAtendimento(atendimento);
        } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Não Salvou o atendimento", "", 0);

        }
//        }
    }

    public List<Atendimento> getListAtendimentos() {
        return listAtendimentos;
    }

    public void setListAtendimentos(List<Atendimento> listAtendimentos) {
        this.listAtendimentos = listAtendimentos;
    }

}
