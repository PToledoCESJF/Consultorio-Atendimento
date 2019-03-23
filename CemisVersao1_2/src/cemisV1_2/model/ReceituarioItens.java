package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class ReceituarioItens implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idItensReceituario;
    private Integer receituario;
    private Integer ordem;
    private Integer medicamento;
    private String quantidade;
    private String posologia;
    private String dose;
    private String tipoUso;

    private Collection<Receituario> receituarioCollection;

    public ReceituarioItens() {
    }

    public ReceituarioItens(Integer idItensReceituario) {
        this.idItensReceituario = idItensReceituario;
    }

    public ReceituarioItens(Integer idItensReceituario, Integer ordem, String quantidade, String posologia, String dose, String tipoUso) {
        this.idItensReceituario = idItensReceituario;
        this.ordem = ordem;
        this.quantidade = quantidade;
        this.posologia = posologia;
        this.dose = dose;
        this.tipoUso = tipoUso;
    }

    public Integer getIdItensReceituario() {
        return idItensReceituario;
    }

    public Integer getReceituario() {
        return receituario;
    }

    public void setReceituario(Integer receituario) {
        this.receituario = receituario;
    }

    public void setIdItensReceituario(Integer idItensReceituario) {
        this.idItensReceituario = idItensReceituario;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public Collection<Receituario> getReceituarioCollection() {
        return receituarioCollection;
    }

    public void setReceituarioCollection(Collection<Receituario> receituarioCollection) {
        this.receituarioCollection = receituarioCollection;
    }

    public Integer getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Integer medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItensReceituario != null ? idItensReceituario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceituarioItens)) {
            return false;
        }
        ReceituarioItens other = (ReceituarioItens) object;
        return !((this.idItensReceituario == null && other.idItensReceituario != null) || (this.idItensReceituario != null && !this.idItensReceituario.equals(other.idItensReceituario)));
    }

    @Override
    public String toString() {
        return "cemis.model.Itensreceituario[ idItensReceituario=" + idItensReceituario + " ]";
    }

}
