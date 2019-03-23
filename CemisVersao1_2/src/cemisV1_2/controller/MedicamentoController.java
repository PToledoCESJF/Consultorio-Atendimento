package cemisV1_2.controller;

import cemisV1_2.model.Medicamento;
import cemisV1_2.model.dao.MedicamentoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicamentoController {

    private List<Medicamento> listMedicamentos = new ArrayList<>();
    private MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    private Medicamento medicamento = new Medicamento();
    private int opcao = 0;

    public MedicamentoController() {

    }

    public List<Medicamento> listarMedicamentos() {
        try {
            return listMedicamentos = medicamentoDAO.listarTodos();
        } catch (Exception ex) {
            Logger.getLogger(MedicamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Medicamento> pesquisarMedicamento(String pesquisa) throws Exception {
        return listMedicamentos = medicamentoDAO.pesquiarMedicamento(pesquisa);

    }

    public boolean inserirMedicamento(Medicamento medicamento) {
        try {
            medicamentoDAO.novoMedicamento(medicamento);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean excluirMedicamento(int idMedicamento) throws Exception {
        if (idMedicamento > 0) {
            return medicamentoDAO.excluirMedicamento(idMedicamento);
        }
        return false;
    }

    public int salvarMedicamento(String idMedicamento, String principioAtivo,
            String medicamento, String laboratorio, String apresentacao,
            boolean botaoClicado) {

        if (validarDados(principioAtivo, medicamento)) {

            this.medicamento.setPrincipioAtivo(principioAtivo);
            this.medicamento.setMedicamento(medicamento);
            this.medicamento.setLaboratorio(laboratorio);
            this.medicamento.setApresentacao(apresentacao);

            if (botaoClicado) {
                try {
                    medicamentoDAO.novoMedicamento(this.medicamento);
                    opcao = 1;
                } catch (Exception ex) {
                    opcao = 2;
                }
            } else {
                try {
                    this.medicamento.setIdMedicamento(Integer.parseInt(idMedicamento));
                    medicamentoDAO.atualizarMedicamento(this.medicamento);
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

    public int salvarMedicamento(String medicamento, String principioAtivo) {

        if (validarDados(medicamento, principioAtivo)) {

            this.medicamento.setMedicamento(medicamento);
            this.medicamento.setPrincipioAtivo(principioAtivo);

            try {
                medicamentoDAO.novoMedicamento(this.medicamento);
                opcao = 1;
            } catch (Exception ex) {
                opcao = 2;
            }
        } else {
            opcao = 5;
        }
        return opcao;
    }

    public int getIdMedicamentoByDesc(String Medicamento) {
        int id = -1;
        try {
            id = medicamentoDAO.listarPorId(Medicamento);
        } catch (SQLException ex) {
            Logger.getLogger(ExameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getMedicamentoById(int idMedicamento) {
        String medicamento = "";
        try {
            medicamento = medicamentoDAO.listarPorMedicamento(idMedicamento);
        } catch (SQLException ex) {
            Logger.getLogger(ExameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return medicamento;
    }

    public boolean validarDados(String principioAtivo, String medicamento) {
        return !(principioAtivo.equals("") || medicamento.equals(""));
    }

    public List<Medicamento> getListMedicamentos() {
        return listMedicamentos;
    }

    public void setListMedicamentos(List<Medicamento> listMedicamentos) {
        this.listMedicamentos = listMedicamentos;
    }
}
