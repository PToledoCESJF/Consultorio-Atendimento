package cemisV1_2.model;

import java.io.Serializable;
import java.util.Date;

public class Atestado implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idAtestado;
    private int paciente;
    private int modeloAtestado;
    private Date dataAtestado;
    private String texto;

    public Atestado() {
        
    }

    public Atestado(int paciente, int modeloAtestado, Date dataAtestado, String texto) {
        this.paciente = paciente;
        this.modeloAtestado = modeloAtestado;
        this.dataAtestado = dataAtestado;
        this.texto = texto;
    }

    public Integer getIdAtestado() {
        return idAtestado;
    }

    public void setIdAtestado(Integer idAtestado) {
        this.idAtestado = idAtestado;
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

    public Date getDataAtestado() {
        return dataAtestado;
    }

    public void setDataAtestado(Date dataAtestado) {
        this.dataAtestado = dataAtestado;
    }

    public int getModeloAtestado() {
        return modeloAtestado;
    }

    public void setModeloAtestado(int modeloAtestado) {
        this.modeloAtestado = modeloAtestado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtestado != null ? idAtestado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atestado)) {
            return false;
        }
        Atestado other = (Atestado) object;
        if ((this.idAtestado == null && other.idAtestado != null) || (this.idAtestado != null && !this.idAtestado.equals(other.idAtestado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemisV1_2.modelo.model.Atestado[ idAtestado=" + idAtestado + " ]";
    }
    
}
