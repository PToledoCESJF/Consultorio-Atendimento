package cemisV1_2.model;

import java.io.Serializable;

/**
 *
 * @author ptol1
 */
public class PedidoExameItens implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer idPedidoExameItens;
    private Integer pedidoExame;
    private Integer exame;

    public PedidoExameItens() {
    }

    public PedidoExameItens(Integer idPedidoExameItens, Integer pedidoExame, Integer exame) {
        this.idPedidoExameItens = idPedidoExameItens;
        this.pedidoExame = pedidoExame;
        this.exame = exame;
    }

    public Integer getIdPedidoExameItens() {
        return idPedidoExameItens;
    }

    public void setIdPedidoExameItens(Integer idPedidoExameItens) {
        this.idPedidoExameItens = idPedidoExameItens;
    }

    public Integer getPedidoExame() {
        return pedidoExame;
    }

    public void setPedidoExame(Integer pedidoExame) {
        this.pedidoExame = pedidoExame;
    }

    public Integer getExame() {
        return exame;
    }

    public void setExame(Integer exame) {
        this.exame = exame;
    }
    
    @Override
    public String toString() {
        return "Item = " + exame + " ]";
    }


}
