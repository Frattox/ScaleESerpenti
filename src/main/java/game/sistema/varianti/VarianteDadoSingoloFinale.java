package game.sistema.varianti;

import elementi.Pedina;
import game.sistema.SistemaImpl1;

//SINGLETON
public class VarianteDadoSingoloFinale implements Variante{

    private VarianteDadoSingoloFinale(){}

    public static Variante getInstance(){return new VarianteDadoSingoloFinale();}

    @Override
    public void action(SistemaImpl1 s) {
        //controllo
        if(s.isDadoSingolo() && s.isDadoSingoloFinale()){
            //azione
            Pedina[] pedine = s.getPedine();
            int turno = s.getTurno();
            int pos = pedine[turno].getCasella().getPos();
            int totCaselle = s.getTotCaselle();

            //se sono le ultime caselle lancierà una solo dado, altrimenti 2
            s.lancia(isUltimeCaselle(pos,totCaselle));
        }
    }

    private boolean isUltimeCaselle(int pos, int totCaselle) {
        return pos>=totCaselle-6;
    }
}
