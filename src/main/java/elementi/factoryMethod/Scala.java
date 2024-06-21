package elementi.factoryMethod;

import elementi.Casella;
import elementi.Tabellone;

public class Scala extends Mezzo{

    public Scala(){super();}

    public Scala(Casella from, Casella to) {
        super(from, to);
    }

    @Override
    protected void caselleBuone(Tabellone tabellone, Casella from, Casella to){
        int c = tabellone.getC();
        int r = tabellone.getR();
        int totCaselle = r*c;
        this.from = casellaRandomDaA(tabellone,1,totCaselle-c);// from appartiene al range [secondaCasella,ultimaCasellaPenultimaRiga)
        Posizione posFrom = tabellone.getPosCasella(this.from);
        this.to = casellaRandomDaA(tabellone,c*(posFrom.getX()+1),totCaselle-2);// to appartiente al range [primaCasellaRigaDopoDiPosFrom, ultimaCasella)
    }

    @Override
    public void caselleCorrette(Casella from, Casella to) {
        if(from.compareTo(to)>=0)
            throw new IllegalArgumentException("Scala: Caselle non idonee");
    }

}
