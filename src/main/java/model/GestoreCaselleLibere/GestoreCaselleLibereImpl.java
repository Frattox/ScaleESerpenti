package model.GestoreCaselleLibere;

import model.elementi.Casella;
import model.elementi.Tabellone;
import model.sistema.SistemaImpl1;

import java.util.ArrayList;

public class GestoreCaselleLibereImpl implements GestoreCaselleLibere {
    private final SistemaImpl1 s;
    private final ArrayList<Casella> caselleLibere;

    public GestoreCaselleLibereImpl(SistemaImpl1 s)
    {
        this.s = s;
        caselleLibere = new ArrayList<>();
        Tabellone tabellone = s.getTabellone();
        for(int i=0;i<tabellone.getR();i++)
            for(int j=0;j< tabellone.getC();j++)
                caselleLibere.add(tabellone.getCasella(i,j));
        //esclusa la pos (0,0), ovvero la prima casella
        caselleLibere.remove(0);
        //esclusa la pos (R-1,C-2), ovvero l'ultima casella
        caselleLibere.remove(caselleLibere.size()-1);
    }


    @Override
    public void setRandomCasellaLibera(Casella.Tipo tipo) {
        int i = (int) (Math.random()*getSize());
        Casella casellaDaSettare = caselleLibere.get(i);
        s.getTabellone().getCasella(casellaDaSettare.getPos()).setTipo(tipo);
        caselleLibere.remove(i);
    }

    @Override
    public Casella setCasellaLibera(int i, Casella.Tipo tipo) {
        Casella casellaDaSettare = caselleLibere.get(i);
        s.getTabellone().getCasella(casellaDaSettare.getPos()).setTipo(tipo);
        caselleLibere.remove(i);
        return casellaDaSettare;
    }

    @Override
    public int getSize() {
        return caselleLibere.size();
    }
}
