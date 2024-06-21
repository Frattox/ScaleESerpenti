package elementi.factoryMethod;

import elementi.Casella;
import elementi.Tabellone;

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

    public void setCaselle(Casella from, Casella to) {
        caselleCorrette(from, to);
        this.from = from;
        this.from.setTipo(Casella.Tipo.MEZZO);
        this.to = to;
        this.to.setTipo(Casella.Tipo.MEZZO);
    }

    public void autoSet(Tabellone tabellone){
        Casella from = new Casella();
        Casella to = new Casella();
        caselleBuone(tabellone,from,to);
        setCaselle(from,to);
    }

    protected Casella casellaRandomDaA(Tabellone tabellone, int start, int end){
        Casella ret;
        do {
            int pos = start + (int) (Math.random() * end);
            Posizione position = tabellone.getPosCasella(pos);
            ret = tabellone.getCasella(position.getX(), position.getY());
        } while (ret.isCovered());
        return ret;
    }

    protected abstract void caselleBuone(Tabellone tabellone, Casella from, Casella to);

    public abstract void caselleCorrette(Casella from, Casella to);
}
