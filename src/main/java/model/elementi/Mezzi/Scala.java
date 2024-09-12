package model.elementi.Mezzi;

import model.elementi.Casella;

public class Scala extends Mezzo{

    public Scala(){super();}

    public Scala(Casella from, Casella to) {
        super(from, to);
    }

    protected int casellaFrom(int nCaselleLibere) {
        // Genera una posizione casuale per iFrom nell'intervallo [0, nCaselleLibere - 2]
        return (int) (Math.random() * (nCaselleLibere - 1));
    }

    protected int casellaTo(int iFrom, int nCaselleLibere) {
        // Genera una posizione casuale per iTo nell'intervallo [iFrom + 1, nCaselleLibere - 1]
        return iFrom + 1 + (int) (Math.random() * (nCaselleLibere - iFrom - 1));
    }


    @Override
    public void caselleCorrette(Casella from, Casella to) {
        if(from.compareTo(to)>=0)
            throw new IllegalArgumentException("Scala: Caselle non idonee");
    }

    @Override
    public TipoMezzo getTipo(){return TipoMezzo.SCALA;}

}
