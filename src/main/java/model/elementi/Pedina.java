package model.elementi;

public class Pedina {
    private Casella curr;
    private int sosta;
    private boolean divietoDiSosta;

    public Pedina(Casella curr) {
        this.curr = curr;
        sosta = 0;
        divietoDiSosta = false;
    }

    public Casella getCasella(){
        return curr;
    }
    public void setPos(Casella curr) {
        this.curr = curr;
    }
    public void setDivietoDiSosta(boolean divietoDiSosta){this.divietoDiSosta=divietoDiSosta;}
    public int getSosta(){return sosta;}
    public void setSosta(int sosta){
        if(sosta<0)
            throw new IllegalArgumentException("Pedina: inserire una sosta opportuna.");
        this.sosta = sosta;
    }

    public void decSosta(){sosta--;}

    public boolean isInSosta(){return sosta>0;}
    public boolean isInDivietoDiSosta(){return divietoDiSosta;}
}
