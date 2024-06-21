package elementi;

public class Casella {

    public enum Tipo{NORMALE,MEZZO,PANCHINA,LOCANDA,DADI,MOLLA}
    private Tipo tipo;

    //rappresenta il numero dove si trova la casella nel tabellone
    //VANTAGGI: comodo per confronti, ricerca costante
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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
