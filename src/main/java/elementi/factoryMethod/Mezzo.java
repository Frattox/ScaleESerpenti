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
        this.from.coverUp();
        this.to.coverUp();
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
        this.to = to;
    }

    public void autoSet(Tabellone tabellone){
        Casella from = new Casella();
        Casella to = new Casella();
        caselleBuone(tabellone,from,to);
        this.from = from;
        this.to = to;
    }

    protected abstract void caselleBuone(Tabellone tabellone, Casella from, Casella to);

    public abstract void caselleCorrette(Casella from, Casella to);
}
