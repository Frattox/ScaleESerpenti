package elementi.factoryMethod;

import elementi.Casella;
import elementi.Posizione;
import elementi.Tabellone;
import game.sistema.SistemaImpl1;

import java.util.ArrayList;

//Classe astratta che definisce elementi di "movimento" da una casella ad un'altra della tabella
public abstract class Mezzo {

    protected Casella from, to;

    protected Mezzo(){
        this.from = new Casella();
        this.to = new Casella();
    }

    protected Mezzo(Casella from, Casella to) {
        if(from.isCovered() || to.isCovered())
            throw new IllegalArgumentException("Mezzo: Caselle già coperte");
        this.caselleCorrette(from,to);
        this.from = from;
        this.to = to;
        this.from.setTipo(Casella.Tipo.MEZZO);
        this.to.setTipo(Casella.Tipo.MEZZO);
    }

    public Casella getFrom() {
        return from;
    }

    public Casella getTo() {
        return to;
    }

    protected void setCaselle(Casella from, Casella to) {
        if(this.from!=null)
            this.from.setTipo(Casella.Tipo.NORMALE);
        if(this.to!=null)
            this.to.setTipo(Casella.Tipo.NORMALE);
        caselleCorrette(from, to);
        this.from = from;
        this.from.setTipo(Casella.Tipo.MEZZO);
        this.to = to;
        this.to.setTipo(Casella.Tipo.MEZZO);
    }

    public void autoSet(SistemaImpl1 s){
        int nCaselleLibere = s.getSizeCaselleLibere();
        int iFrom = casellaFrom(nCaselleLibere);
        int iTo = casellaTo(iFrom,nCaselleLibere);
        s.setCasellaLibera(iFrom, Casella.Tipo.MEZZO);
        s.setCasellaLibera(iTo, Casella.Tipo.MEZZO);
    }

    protected abstract void caselleCorrette(Casella from, Casella to);

    protected abstract int casellaFrom(int nCaselleLibere);

    protected abstract int casellaTo(int iFrom, int nCaselleLibere);
}
