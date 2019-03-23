package cemisV1_2.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class Receituario implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idReceituario;
    private Integer paciente;
    private Date dataReceituario;

    public Receituario() {
    }

    public Receituario(Integer idReceituario) {
        this.idReceituario = idReceituario;
    }

    public Receituario(Integer idReceituario, Integer paciente, Date data) {
        this.idReceituario = idReceituario;
        this.paciente = paciente;
        this.dataReceituario = data;
    }

    public Integer getIdReceituario() {
        return idReceituario;
    }

    public void setIdReceituario(Integer idReceituario) {
        this.idReceituario = idReceituario;
    }

    public Date getDataReceituario() {
        return dataReceituario;
    }

    public void setDataReceituario(Date dataReceituario) {
        this.dataReceituario = dataReceituario;
    }

    public Integer getPaciente() {
        return paciente;
    }

    public void setPaciente(Integer paciente) {
        this.paciente = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceituario != null ? idReceituario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receituario)) {
            return false;
        }
        Receituario other = (Receituario) object;
        if ((this.idReceituario == null && other.idReceituario != null) || (this.idReceituario != null && !this.idReceituario.equals(other.idReceituario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemis.model.Receituario[ idReceituario=" + idReceituario + " ]";
    }

}
