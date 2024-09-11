package model.varianti;

import model.elementi.Pedina;
import model.SistemaImpl1;

public class VarianteDoppioSei extends AbstractVariante{

    public VarianteDoppioSei(){}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {
        //controllo: DoppioSei => !DadoSingolo
        if(this.activated && s.isDadoSingolo())
            throw new IllegalArgumentException("VarianteDadoSingoloFinale: non puoi attivare questa variante insieme a VarianteDadoSingolo");
        this.activated = activated;
    }

    //NO DADO SINGOLO + l'ultimo lancio è doppio 6 + IS DOPPIO SEI => non effettuo l'avanzamento del turno
    @Override
    public void action(SistemaImpl1 s) {
        //controllo: DoppioSei => !DadoSingolo
        if(!s.isDadoSingolo() && this.isActivated()){
            //azione
            int lancio = s.getLancio();
            if(isLancioDoppioSei(lancio))
                return;
        }
        int turno = s.getTurno();
        Pedina[] pedine = s.getPedine();
        turno = (turno+1)%pedine.length; //così turno rimane nel range [0,pedine.lenght-1]
        s.setTurno(turno);
    }

    private boolean isLancioDoppioSei(int lancio){
        return lancio!=0 && lancio%6==0;
    }
}
