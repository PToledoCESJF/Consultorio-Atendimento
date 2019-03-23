package cemisV1_2.controller;

import cemisV1_2.model.Paciente;
import cemisV1_2.model.dao.PacienteDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteController {

    private List<Paciente> listPacientes = new ArrayList<>();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private Paciente paciente = new Paciente();
    private int opcao = 0;

    public PacienteController() {

    }

    public List<Paciente> listarPacientes() {
        try {
            return listPacientes = pacienteDAO.listarTodos();
        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Paciente> pesquisarPacientes(String pesquisa) throws Exception {
        return listPacientes = pacienteDAO.pesquiarPaciente(pesquisa);

    }

    public ResultSet relatorioPacientes() throws SQLException {
        ResultSet relatResultado = pacienteDAO.relatorioPacientes();
        return relatResultado;
    }

    public ResultSet relatorioPesquisaPacientes(String pesquisa) throws SQLException {
        ResultSet relatResultado = pacienteDAO.relatorioPesquisaPacientes(pesquisa);
        return relatResultado;
    }

    public boolean inserirPaciente(Paciente paciente) {
        try {
            pacienteDAO.novoPaciente(paciente);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean excluirPaciente(int idPaciente) throws Exception {
        if (idPaciente > 0) {
            return pacienteDAO.excluirPaciente(idPaciente);
        }
        return false;
    }

    public int salvarPaciente(String idPaciente, String nomePaciente,
            String prontuario, String endereco, String cidade, String telefone,
            boolean botaoClicado) {

        if (validarDados(nomePaciente, prontuario)) {

            paciente.setNomePaciente(nomePaciente);
            paciente.setProntuario(prontuario);
            paciente.setEndereco(endereco);
            paciente.setCidade(cidade);
            paciente.setTelefone(telefone);

            if (botaoClicado) {
                try {
                    pacienteDAO.novoPaciente(paciente);
                    opcao = 1;
                } catch (Exception ex) {
                    opcao = 2;
                }
            } else {
                try {
                    paciente.setIdPaciente(Integer.parseInt(idPaciente));
                    pacienteDAO.atualizarPaciente(paciente);
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
    
    public int salvarPaciente(String nomePaciente,String prontuario) {

        if (validarDados(nomePaciente, prontuario)) {

            paciente.setNomePaciente(nomePaciente);
            paciente.setProntuario(prontuario);

            try {
                pacienteDAO.novoPaciente(paciente);
                opcao = 1;
            } catch (Exception ex) {
                opcao = 2;
            }
        } else {
            opcao = 5;
        }
        return opcao;
    }

    public boolean validarDados(String nomePaciente, String prontuario) {
        if (nomePaciente.equals("") || prontuario.equals("")) {
            return false;
        }
        return true;
    }
        
    public String converteIdPacienteEmNomePaciente(int idPaciente){
        String getPaciente = "";
        try {
            
            Paciente p = pacienteDAO.pesquiarPacienteId(idPaciente);
            getPaciente = p.getNomePaciente();
            
        } catch (Exception ex) {
            Logger.getLogger(ReceituarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getPaciente;
    }
    
    public int converteNomePacienteEmIdPaciente(String nomePaciente){
        int getIdPaciente = 0;
        try {
            
            Paciente p = pacienteDAO.pesquiarPacienteNome(nomePaciente);
            getIdPaciente = p.getIdPaciente();
            
        } catch (Exception ex) {
            Logger.getLogger(ReceituarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getIdPaciente;
    }

    public List<Paciente> getListPacientes() {
        return listPacientes;
    }

    public void setListPacientes(List<Paciente> listPacientes) {
        this.listPacientes = listPacientes;
    }

}
