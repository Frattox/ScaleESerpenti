package elementi;

public class Casella {

    //variabile utilizzata dai Mezzi (serpenti o scale) per verificare
    //che la casella non sia una casella speciale oppure già coperta
    //da un'altra scala o serpente
    private boolean covered = false;
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

    public boolean isCovered() {
        return covered;
    }

    public enum Tipo{NORMALE,PANCHINA,LOCANDA,DADI,MOLLA}

    public Casella(){
        this.tipo = Tipo.NORMALE;
        this.covered = false;
        this.pos = -1;
    }

    public Casella(Tipo tipo, int pos){
        this.tipo = tipo;
        if(this.tipo!=Tipo.NORMALE)
            this.covered = true;
        this.pos = pos;
    }

    //metodo utilizzato dai mezzi
    public void coverUp(){this.covered = true;}
    public void uncover(){this.covered = false;}

    public Tipo getTipo(){return this.tipo;}

    public int compareTo(Casella c){
        return Integer.compare(this.pos, c.pos);
    }

}
