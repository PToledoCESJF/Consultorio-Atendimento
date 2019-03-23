package cemisV1_2.model;

import java.io.Serializable;

public class AtestadoModelo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idModAtestado;
    private String titulo;
    private String texto;

    public AtestadoModelo() {
        
    }

    public AtestadoModelo(Integer idModAtestado) {
        this.idModAtestado = idModAtestado;
    }

    public AtestadoModelo(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    
    public Integer getIdModAtestado() {
        return idModAtestado;
    }
    
    public void setIdModAtestado(Integer idModAtestado) {
        this.idModAtestado = idModAtestado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        hash += (idModAtestado != null ? idModAtestado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtestadoModelo)) {
            return false;
        }
        AtestadoModelo other = (AtestadoModelo) object;
        if ((this.idModAtestado == null && other.idModAtestado != null) || (this.idModAtestado != null && !this.idModAtestado.equals(other.idModAtestado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemisV1_2.modelo.model.Atestadomodelo[ idModAtestado=" + titulo + " ]";
    }
    
}
