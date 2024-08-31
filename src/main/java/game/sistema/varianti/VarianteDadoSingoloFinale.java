package game.sistema.varianti;

import elementi.Dado;
import elementi.Pedina;
import game.sistema.SistemaImpl1;

import java.util.List;

//SINGLETON
public class VarianteDadoSingoloFinale extends AbstractVariante{

    private static Variante varianteDadoSingoloFinale;

    private VarianteDadoSingoloFinale(){}

    public static Variante getInstance(){
        if(varianteDadoSingoloFinale==null)
            varianteDadoSingoloFinale = new VarianteDadoSingoloFinale();
        return varianteDadoSingoloFinale;
    }

    @Override
    public void action(SistemaImpl1 s) {
        int lancio = s.getLancio();
        int indiceDado = 0;
        List<Dado> dadi = s.getDadi();

        //a prescindere effettua il lancio di un dado almeno
        lancio += dadi.get(indiceDado).lancia();
        indiceDado++;

        //controllo: DadoSingoloFinale => !DadoSingolo
        if(!s.isDadoSingolo() && this.isActivated()){
            //azione
            Pedina[] pedine = s.getPedine();
            int turno = s.getTurno();
            int pos = pedine[turno].getCasella().getPos();
            int totCaselle = s.getTotCaselle();
            //Se non sono alle ultime caselle, allora lancio entrambi i dadi
            if(!isUltimeCaselle(pos,totCaselle)){
                lancio += dadi.get(indiceDado).lancia();
            }
        }
        s.setLancio(lancio);
    }

    private boolean isUltimeCaselle(int pos, int totCaselle) {
        return pos>=totCaselle-6;
    }
}
