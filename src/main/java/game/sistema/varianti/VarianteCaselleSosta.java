package game.sistema.varianti;

import elementi.Casella;
import elementi.Tabellone;
import game.sistema.SistemaImpl1;

import java.util.ArrayList;

public class VarianteCaselleSosta extends AbstractVariante{

    public VarianteCaselleSosta(){}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {this.activated=activated;}


    //Applica le caselle sosta sul tabellone
    @Override
    public void action(SistemaImpl1 s) {
        if(!this.isActivated())
            return;
        int nCaselleSosta = s.getnCaselleSosta();
        for(int i=0;i<nCaselleSosta;i++){
            s.setRandomCasellaLibera(tipoSostaRandom());
        }
    }

    private Casella.Tipo tipoSostaRandom()
    {
        int x = (int) (Math.random()*2);
        if(x==0)
            return Casella.Tipo.PANCHINA;
        return Casella.Tipo.LOCANDA;
    }
}
