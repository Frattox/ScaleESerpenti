package model.elementi;



public class Dado {
    private final int facciate;

    public Dado(){this.facciate=6;} //facciate di default

    public Dado(int facciate){this.facciate=facciate;}

    public int lancia(){return (int)(1+Math.random()*facciate);}
}
