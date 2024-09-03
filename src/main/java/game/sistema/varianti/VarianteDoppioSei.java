package game.sistema.varianti;

import elementi.Pedina;
import game.sistema.SistemaImpl1;

//SINGLETON
public class VarianteDoppioSei extends AbstractVariante{

    private static Variante varianteDoppioSei;

    private VarianteDoppioSei(){}

    public static Variante getInstance(){
        if(varianteDoppioSei==null)
            varianteDoppioSei = new VarianteDoppioSei();
        return varianteDoppioSei;
    }

    //NO DADO SINGOLO + l'ultimo lancio è doppio 6 + IS DOPPIO SEI => non effettuo l'avanzamento del turno
    @Override
    public void action(SistemaImpl1 s) {
        //controllo: DoppioSei => !DadoSingolo
        if(!s.isDadoSingolo() && varianteDoppioSei.isActivated()){
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
        return lancio%6==0;
    }
}
