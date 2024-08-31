package elementi;

public class Pedina {
    private static final int SOSTA_PANCHINA = 1, SOSTA_LOCANDA = 3;
    private Casella curr;
    private int sosta;

    public Pedina(Casella curr) {
        this.curr = curr;
        sosta = 0;
    }

    public Casella getCasella(){
        return curr;
    }
    public void setPos(Casella curr) {
        this.curr = curr;
    }
    public int getSosta(){return sosta;}
    public void setSosta(){
        Casella curr = this.getCasella();
        if(curr.getTipo()== Casella.Tipo.PANCHINA)
            this.setSosta(SOSTA_PANCHINA);
        else if(curr.getTipo()== Casella.Tipo.LOCANDA)
            this.setSosta(SOSTA_LOCANDA);
    }
    public void setSosta(int sosta){
        if(sosta<=0)
            throw new IllegalArgumentException("Pedina: inserire una sosta positiva.");
        this.sosta = sosta;
    }
    public void decSosta(){sosta--;}
    public boolean isInSosta(){return sosta>0;}
}
