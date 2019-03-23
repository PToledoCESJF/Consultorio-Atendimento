package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class Patologia implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPatologia;
    private String patologia;

    public Patologia() {
    }

    public Patologia(Integer idPatologia) {
        this.idPatologia = idPatologia;
    }

    public Patologia(Integer idPatologia, String patologia) {
        this.idPatologia = idPatologia;
        this.patologia = patologia;
    }

    public Integer getIdPatologia() {
        return idPatologia;
    }

    public void setIdPatologia(Integer idPatologia) {
        this.idPatologia = idPatologia;
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatologia != null ? idPatologia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patologia)) {
            return false;
        }
        Patologia other = (Patologia) object;
        if ((this.idPatologia == null && other.idPatologia != null) || (this.idPatologia != null && !this.idPatologia.equals(other.idPatologia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemis.model.Patologia[ idPatologia=" + idPatologia + " ]";
    }

}
