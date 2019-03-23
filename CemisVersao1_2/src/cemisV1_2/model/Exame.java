package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idExame;
    private String exame;
    private Integer tipoexame;

    private Collection<Pedidoexame> pedidoexameCollection;

    public Exame() {
    }

    public Exame(Integer idExame) {
        this.idExame = idExame;
    }

    public Exame(Integer idExame, String exame) {
        this.idExame = idExame;
        this.exame = exame;
    }

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public String getExame() {
        return exame;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public Integer getTipoexame() {
        return tipoexame;
    }

    public void setTipoexame(Integer tipoexame) {
        this.tipoexame = tipoexame;
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
        hash += (idExame != null ? idExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exame)) {
            return false;
        }
        Exame other = (Exame) object;
        if ((this.idExame == null && other.idExame != null) || (this.idExame != null && !this.idExame.equals(other.idExame))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return exame;
    }
}
