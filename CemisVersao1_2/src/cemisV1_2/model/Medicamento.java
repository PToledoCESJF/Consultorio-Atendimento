package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idMedicamento;
    private String principioAtivo;
    private String medicamento;
    private String laboratorio;
    private String apresentacao;

    private Collection<ReceituarioItens> itensreceituarioCollection;

    public Medicamento() {
    }

    public Medicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Medicamento(Integer idMedicamento, String medicamento) {
        this.idMedicamento = idMedicamento;
        this.medicamento = medicamento;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public Collection<ReceituarioItens> getItensreceituarioCollection() {
        return itensreceituarioCollection;
    }

    public void setItensreceituarioCollection(Collection<ReceituarioItens> itensreceituarioCollection) {
        this.itensreceituarioCollection = itensreceituarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMedicamento != null ? idMedicamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicamento)) {
            return false;
        }
        Medicamento other = (Medicamento) object;
        if ((this.idMedicamento == null && other.idMedicamento != null) || (this.idMedicamento != null && !this.idMedicamento.equals(other.idMedicamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemis.model.Medicamento[ idMedicamento=" + idMedicamento + " ]";
    }

}
