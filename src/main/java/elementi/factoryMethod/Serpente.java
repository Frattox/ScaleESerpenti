package elementi.factoryMethod;

import elementi.Casella;
import elementi.Posizione;
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
    protected int casellaFrom(int nCaselleLibere) {
        return (int) (1 + Math.random()*nCaselleLibere);//casella libera random in [1,nCaselleLibere-1]
    }

    @Override
    protected int casellaTo(int iFrom, int nCaselleLibere){
        return (int) (Math.random()*iFrom);//casella libera random in [0,iFrom-1]
    }

}
