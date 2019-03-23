package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idAtendimento;
    private Integer agenda;
    private Integer medico;
    private Date dataAtendimento;
    private Integer paciente;

    private Collection<Atestado> atestadoCollection;
    private Collection<Receituario> receituarioCollection;
    private Collection<Pedidoexame> pedidoexameCollection;

    public Atendimento() {
    }

    public Atendimento(Integer idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Atendimento(Integer idAtendimento, Integer medico, Date dataAtendimento) {
        this.idAtendimento = idAtendimento;
        this.medico = medico;
        this.dataAtendimento = dataAtendimento;
    }

    public Integer getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Integer idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Integer getAgenda() {
        return agenda;
    }

    public void setAgenda(Integer agenda) {
        this.agenda = agenda;
    }

    public Integer getMedico() {
        return medico;
    }

    public void setMedico(Integer medico) {
        this.medico = medico;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Collection<Atestado> getAtestadoCollection() {
        return atestadoCollection;
    }

    public void setAtestadoCollection(Collection<Atestado> atestadoCollection) {
        this.atestadoCollection = atestadoCollection;
    }

    public Collection<Receituario> getReceituarioCollection() {
        return receituarioCollection;
    }

    public void setReceituarioCollection(Collection<Receituario> receituarioCollection) {
        this.receituarioCollection = receituarioCollection;
    }

    public Integer getPaciente() {
        return paciente;
    }

    public void setPaciente(Integer paciente) {
        this.paciente = paciente;
    }

    public Collection<Pedidoexame> getPedidoexameCollection() {
        return pedidoexameCollection;
    }

    public void setPedidoexameCollection(Collection<Pedidoexame> pedidoexameCollection) {
        this.pedidoexameCollection = pedidoexameCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtendimento != null ? idAtendimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atendimento)) {
            return false;
        }
        Atendimento other = (Atendimento) object;
        if ((this.idAtendimento == null && other.idAtendimento != null) || (this.idAtendimento != null && !this.idAtendimento.equals(other.idAtendimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemis.model.Consulta[ idConsulta=" + idAtendimento + " ]";
    }

}
