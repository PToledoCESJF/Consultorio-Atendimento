package cemisV1_2.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class Pedidoexame implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPedidoExame;
    private Date dataPedidoExame;
    private Integer paciente;

    public Pedidoexame() {
    }

    public Pedidoexame(Integer idPedidoExame) {
        this.idPedidoExame = idPedidoExame;
    }

    public Pedidoexame(Integer idPedidoExame, Date data) {
        this.idPedidoExame = idPedidoExame;
        this.dataPedidoExame = data;
    }

    public Integer getIdPedidoExame() {
        return idPedidoExame;
    }

    public void setIdPedidoExame(Integer idPedidoExame) {
        this.idPedidoExame = idPedidoExame;
    }

    public Date getDataPedidoExame() {
        return dataPedidoExame;
    }

    public void setDataPedidoExame(Date dataPedidoExame) {
        this.dataPedidoExame = dataPedidoExame;
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
        hash += (idPedidoExame != null ? idPedidoExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidoexame)) {
            return false;
        }
        Pedidoexame other = (Pedidoexame) object;
        if ((this.idPedidoExame == null && other.idPedidoExame != null) || (this.idPedidoExame != null && !this.idPedidoExame.equals(other.idPedidoExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemis.model.Pedidoexame[ idPedidoExame=" + idPedidoExame + " ]";
    }

}
