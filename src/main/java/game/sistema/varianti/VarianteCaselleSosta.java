package game.sistema.varianti;

import elementi.Casella;
import elementi.Tabellone;
import game.sistema.SistemaImpl1;

import java.util.ArrayList;

//SINGLETON
public class VarianteCaselleSosta extends AbstractVariante{

    private static Variante varianteCaselleSosta;

    private VarianteCaselleSosta(){}

    public static Variante getInstance(){
        if(varianteCaselleSosta==null)
            varianteCaselleSosta = new VarianteCaselleSosta();
        return varianteCaselleSosta;
    }

    //Applica le caselle sosta sul tabellone
    @Override
    public void action(SistemaImpl1 s) {
        if(!varianteCaselleSosta.isActivated())
            return;
        int nCaselleSosta = s.getnCaselleSosta();
        for(int i=0;i<nCaselleSosta;i++){
            int nCaselleLibere = s.getSizeCaselleLibere();
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
