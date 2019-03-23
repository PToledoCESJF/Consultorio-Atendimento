package cemisV1_2.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paulo
 */
public class ExameTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTipoExame;
    private String tipoExame;

    private Collection<Exame> exameCollection;

    public ExameTipo() {
    }

    public ExameTipo(Integer idTipoExame) {
        this.idTipoExame = idTipoExame;
    }

    public ExameTipo(Integer idTipoExame, String tipoExame) {
        this.idTipoExame = idTipoExame;
        this.tipoExame = tipoExame;
    }

    public Integer getIdTipoExame() {
        return idTipoExame;
    }

    public void setIdTipoExame(Integer idTipoExame) {
        this.idTipoExame = idTipoExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public Collection<Exame> getExameCollection() {
        return exameCollection;
    }

    public void setExameCollection(Collection<Exame> exameCollection) {
        this.exameCollection = exameCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoExame != null ? idTipoExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExameTipo)) {
            return false;
        }
        ExameTipo other = (ExameTipo) object;
        if ((this.idTipoExame == null && other.idTipoExame != null) || (this.idTipoExame != null && !this.idTipoExame.equals(other.idTipoExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoExame;
    }

}
