package game.sistema.Mattaro;

import game.sistema.SistemaImpl1;

public class MattaroImpl implements Mattaro{

    private Carta[] mazzo;
    private final int DEFAULT_MAX_MAZZO = 100;
    private int cima;

    public MattaroImpl(SistemaImpl1 s){
        mazzo = mazzoRandom(s.isUlterioriCarte());
        cima=0;
    }

    private Carta[] mazzoRandom(boolean isUlterioriCarte){
        Carta[] ret = new Carta[DEFAULT_MAX_MAZZO];
        int x;
        Carta[] carte = Carta.values();
        int nCarte = isUlterioriCarte?carte.length:(carte.length-1); //in caso, non si considera l'ultima carta aggiunta
        for(int i=0;i<ret.length;i++){
            x = (int) (Math.random()*nCarte);
            ret[i] = carte[x];
        }
        return ret;
    }

    @Override
    public Carta pesca() {
        Carta ret = mazzo[cima];
        cima = (cima+1)%mazzo.length;
        return ret;
    }
}
