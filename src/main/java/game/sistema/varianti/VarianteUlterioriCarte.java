package game.sistema.varianti;

import game.sistema.SistemaImpl1;

//SINGLETON
public class VarianteUlterioriCarte extends AbstractVariante{

    public VarianteUlterioriCarte(){}

    @Override
    public void action(SistemaImpl1 s) {}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {
        //controllo: UlterioriCarte => PescaCarta
        if(this.isActivated() && !s.isPescaCarta())
            throw new IllegalArgumentException("VarianteUlterioriCarte: non si può attivare se non è anche attiva Pesca Carta");
        this.activated = s.isUlterioriCarte();
    }
}
