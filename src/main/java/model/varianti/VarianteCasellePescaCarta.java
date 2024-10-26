package model.varianti;

import model.elementi.Casella;
import model.sistema.SistemaImpl1;

//SINGLETON
public class VarianteCasellePescaCarta extends AbstractVariante{
    public VarianteCasellePescaCarta(){}

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
