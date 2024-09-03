package elementi.GestoreCaselleLibere;

import elementi.Casella;
import elementi.Tabellone;
import game.sistema.SistemaImpl1;

import java.util.ArrayList;

public class GestoreCaselleLibereImpl implements GestoreCaselleLibere {
    private ArrayList<Casella> caselleLibere;

    public GestoreCaselleLibereImpl(SistemaImpl1 s)
    {
        caselleLibere = new ArrayList<>();
        Tabellone tabellone = s.getTabellone();
        //esclusa la pos (0,0), ovvero la prima casella
        for(int i=1;i<tabellone.getR();i++)
            for(int j=0;j< tabellone.getC();j++)
                caselleLibere.add(tabellone.getCasella(i,j));
        //esclusa la pos (R-1,C-2), ovvero l'ultima casella
        caselleLibere.remove(caselleLibere.size()-1);
    }


    @Override
    public void setRandomCasellaLibera(Casella.Tipo tipo) {
        int i = (int) (Math.random()*getSize());
        caselleLibere.get(i).setTipo(tipo);
        caselleLibere.remove(i);
    }

    @Override
    public void setCasellaLibera(int i, Casella.Tipo tipo) {
        caselleLibere.get(i).setTipo(tipo);
        caselleLibere.remove(i);
    }

    @Override
    public int getSize() {
        return caselleLibere.size();
    }
}