package model.varianti;

import model.elementi.Dado;
import model.elementi.Pedina;
import model.SistemaImpl1;

import java.util.List;

public class VarianteDadoSingoloFinale extends AbstractVariante{

    public VarianteDadoSingoloFinale(){}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {
        //controllo: DadoSingoloFinale => !DadoSingolo
        if(this.activated && s.isDadoSingolo())
            throw new IllegalArgumentException("VarianteDadoSingoloFinale: non puoi attivare questa variante insieme a VarianteDadoSingolo");
        this.activated = activated;
    }

    @Override
    public void action(SistemaImpl1 s) {
        int lancio = s.getLancio();
        int indiceDado = 0;
        List<Dado> dadi = s.getDadi();

        //a prescindere effettua il lancio di un dado almeno
        lancio += dadi.get(indiceDado).lancia();
        indiceDado++;

        Pedina[] pedine = s.getPedine();
        int turno = s.getTurno();
        int pos = pedine[turno].getCasella().getPos();
        int totCaselle = s.getTotCaselle();
        //controllo
        if((!this.isActivated() && !s.isDadoSingolo()) || (this.isActivated() && !isUltimeCaselle(pos,totCaselle))){
            //azione
            //Se non sono alle ultime caselle, allora lancio entrambi i dadi
            lancio += dadi.get(indiceDado).lancia();
        }
        s.setLancio(lancio);
    }

    private boolean isUltimeCaselle(int pos, int totCaselle) {
        return pos>=totCaselle-6;
    }
}
