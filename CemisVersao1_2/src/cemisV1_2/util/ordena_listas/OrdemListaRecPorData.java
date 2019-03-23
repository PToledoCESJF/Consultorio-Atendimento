package cemisV1_2.util.ordena_listas;

import cemisV1_2.model.TmpTabelaRelatorios;
import java.util.Comparator;

public class OrdemListaRecPorData implements Comparator<TmpTabelaRelatorios>{

    @Override
    public int compare(TmpTabelaRelatorios t, TmpTabelaRelatorios t1) {
        return t.getData().compareTo(t1.getData());
    }
}
