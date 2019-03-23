package cemisV1_2.model;

public class ExameTipoOLD {

    private static final long serialVersionUID = 1L;
    private Integer idExame;
    private String exame;
    private String tipoexame;

    public ExameTipoOLD() {
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

    public String getTipoexame() {
        return tipoexame;
    }

    public void setTipoexame(String tipoexame) {
        this.tipoexame = tipoexame;
    }

    @Override
    public String toString() {
        return "ExameTipo{" + "idExame=" + idExame + ", exame=" + exame + ", tipoexame=" + tipoexame + '}';
    }

}
