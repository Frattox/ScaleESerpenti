package model.elementi.Mezzi;

import model.elementi.Casella;

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
        return 1 + (int) (Math.random()*(nCaselleLibere-1));//casella libera random in [1,nCaselleLibere-1]
    }

    @Override
    protected int casellaTo(int iFrom, int nCaselleLibere){
        return (int) (Math.random()*iFrom);//casella libera random in [0,iFrom-1]
    }

}
