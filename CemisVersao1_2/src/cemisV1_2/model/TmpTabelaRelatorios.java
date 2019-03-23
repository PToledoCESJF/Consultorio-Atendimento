package cemisV1_2.model;

import java.util.Date;

public class TmpTabelaRelatorios {
    private Integer id;
    private String nomePaciente;
    private Date data;

    public TmpTabelaRelatorios(Integer id, String nomePaciente, Date data) {
        this.id = id;
        this.nomePaciente = nomePaciente;
        this.data = data;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
