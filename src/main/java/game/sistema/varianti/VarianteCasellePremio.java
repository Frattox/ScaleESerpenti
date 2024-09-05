package game.sistema.varianti;

import elementi.Casella;
import game.sistema.SistemaImpl1;

public class VarianteCasellePremio extends AbstractVariante{
    public VarianteCasellePremio(){}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {this.activated=activated;}

    @Override
    public void action(SistemaImpl1 s){
        if(!this.isActivated())
            return;
        int nCasellePremio = s.getnCasellePremio();
        for(int i=0;i<nCasellePremio;i++){
            s.setRandomCasellaLibera(tipoPremioRandom());
        }
    }

    private Casella.Tipo tipoPremioRandom()
    {
        int x = (int) (Math.random()*2);
        if(x==0)
            return Casella.Tipo.DADI;
        return Casella.Tipo.MOLLA;
    }


}
