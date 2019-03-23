package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class Paciente implements Serializable {

    private Collection<Atendimento> consultaCollection;

    private static final long serialVersionUID = 1L;
    private Integer idPaciente;
    private String nomePaciente;
    private String prontuario;
    private String endereco;
    private String cidade;
    private String telefone;

    public Paciente() {
    }

    public Paciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Paciente(Integer idPaciente, String nomePaciente) {
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
    }

    public Paciente(Integer idPaciente, String nomePaciente, String prontuario) {
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.prontuario = prontuario;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nPaciente: " + idPaciente + " - " +nomePaciente;
    }

    public Collection<Atendimento> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Atendimento> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

}
