package elementi.Mezzi;

import elementi.Casella;
import game.sistema.SistemaImpl1;

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
        this.from.setTipo(Casella.Tipo.MEZZO_FROM);
        this.to.setTipo(Casella.Tipo.MEZZO_TO);
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
        this.from.setTipo(Casella.Tipo.MEZZO_FROM);
        this.to = to;
        this.to.setTipo(Casella.Tipo.MEZZO_TO);
    }

    public void autoSet(SistemaImpl1 s){
        int nCaselleLibere = s.getSizeCaselleLibere();
        int iFrom = casellaFrom(nCaselleLibere);
        int iTo = casellaTo(iFrom,nCaselleLibere);
        s.setCasellaLibera(iFrom, Casella.Tipo.MEZZO_FROM);
        s.setCasellaLibera(iTo, Casella.Tipo.MEZZO_TO);
    }

    protected abstract void caselleCorrette(Casella from, Casella to);

    protected abstract int casellaFrom(int nCaselleLibere);

    protected abstract int casellaTo(int iFrom, int nCaselleLibere);
}
