package model.varianti;

import model.elementi.Casella;
import model.Mattaro.Mattaro;
import model.Mattaro.MattaroImpl;
import model.SistemaImpl1;

//SINGLETON
public class VarianteCasellePescaCarta extends AbstractVariante{
    private Mattaro mattaro;
    public VarianteCasellePescaCarta(SistemaImpl1 s){mattaro = new MattaroImpl(s);}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {this.activated=activated;}

    @Override
    public void action(SistemaImpl1 s) {
        if(!this.isActivated())
            return;
        int nCasellePescaCarta = s.getnCasellePescaCarta();
        for(int i=0;i<nCasellePescaCarta;i++){
            s.setRandomCasellaLibera(Casella.Tipo.PESCA);
        }
    }
}
