package elementi.factoryMethod;

import elementi.Casella;
import elementi.Posizione;
import elementi.Tabellone;

public class Scala extends Mezzo{

    public Scala(){super();}

    public Scala(Casella from, Casella to) {
        super(from, to);
    }

    protected int casellaFrom(int nCaselleLibere){return (int) (Math.random()*nCaselleLibere-1);}
    protected int casellaTo(int iFrom, int nCaselleLibere) {return (int) (iFrom + (Math.random()*nCaselleLibere));} //casella libera random in [iFrom,nCaselleLibere-1]


    @Override
    public void caselleCorrette(Casella from, Casella to) {
        if(from.compareTo(to)>=0)
            throw new IllegalArgumentException("Scala: Caselle non idonee");
    }

}
