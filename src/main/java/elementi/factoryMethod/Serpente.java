package elementi.factoryMethod;

import elementi.Casella;
import elementi.Tabellone;

public class Serpente extends Mezzo{

    public Serpente(){super();}

    public Serpente(Casella from, Casella to){
        super(from, to);
    }

    @Override
    public void caselleCorrette(Casella from, Casella to) {
        if(from.compareTo(to)<=0)
            throw new IllegalArgumentException("Serpente: Caselle non idonee");
    }


    @Override
    protected void caselleBuone(Tabellone tabellone, Casella from, Casella to){
        int c = tabellone.getC();
        int r = tabellone.getR();
        int totCaselle = r*c;
        this.from = casellaRandomDaA(tabellone,c,totCaselle-2);// from appartiene al range [primaCellaSecondaRiga,ultimaCasella)
        Posizione posFrom = tabellone.getPosCasella(this.from);
        this.to = casellaRandomDaA(tabellone,1,c*posFrom.getX()-1);// to appartiente al range [secondaCasella, ultimaCasellaPrimaDellaRigaDiNewFrom]
    }

}
