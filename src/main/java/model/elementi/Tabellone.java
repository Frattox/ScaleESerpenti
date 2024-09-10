package model.elementi;

public abstract class Tabellone {

    protected int r,c;

    protected final int R_DEFAULT = 10, C_DEFAULT = 10;

    protected Tabellone()
    {
        this.r = this.R_DEFAULT;
        this.c = this.C_DEFAULT;
    }

    protected Tabellone(int r, int c)
    {
        if(r<1 || c<1) throw new IllegalArgumentException("Tabellone: numero di righe o colonne invalido");
        this.r = r;
        this.c = c;
    }

    public Posizione getPosCasella(Casella casella){
        if(casella.getPos()>=this.r*this.c)
            throw new IllegalArgumentException("Tabellone: casella non appartenente alla tabella");
        int x = casella.getPos()/this.c;
        int y = casella.getPos()%this.c;
        return new Posizione(x,y);
    }

    public Posizione getPosCasella(int pos){
        if(pos>=this.r*this.c)
            throw new IllegalArgumentException("Tabellone: posizione "+pos+" non appartenente alla tabella r:"+this.r+" c:"+this.c);
        int x = pos/this.c;
        int y = pos%this.c;
        return new Posizione(x,y);
    }

    public abstract Casella getCasella(int i, int j);
    public abstract Casella getCasella(int pos);


    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }


}
