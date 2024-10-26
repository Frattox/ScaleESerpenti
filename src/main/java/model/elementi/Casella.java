package model.elementi;

public class Casella {

    public enum Tipo{INIZIO,FINE,NORMALE,MEZZO_FROM,MEZZO_TO,PANCHINA,LOCANDA,DADI,MOLLA,PESCA}
    private Tipo tipo;

    //rappresenta il numero dove si trova la casella nel tabellone
    //VANTAGGI: comodo per confronti, ricerca costante
    private final int pos;

    public int getPos() {
        return pos;
    }

    public boolean isCovered() {return tipo!=Tipo.NORMALE;}

    public Casella(){
        this.tipo = Tipo.NORMALE;
        this.pos = -1;
    }

    public Casella(Tipo tipo, int pos){
        this.tipo = tipo;
        this.pos = pos;
    }
    public void setTipo(Tipo tipo){this.tipo = tipo;}

    public Tipo getTipo(){return this.tipo;}

    public int compareTo(Casella c){
        return Integer.compare(this.pos, c.pos);
    }

}
