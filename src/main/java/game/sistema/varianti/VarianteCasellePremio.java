package game.sistema.varianti;

import elementi.Casella;
import game.sistema.SistemaImpl1;


//SINGLETON
public class VarianteCasellePremio extends AbstractVariante{
    private static Variante varianteCasellePremio;

    private VarianteCasellePremio(){}

    public static Variante getInstance(){
        if(varianteCasellePremio==null)
            varianteCasellePremio = new VarianteCasellePremio();
        return varianteCasellePremio;
    }

    @Override
    public void action(SistemaImpl1 s){
        if(!varianteCasellePremio.isActivated())
            return;
        int nCasellePremio = s.getnCasellePremio();
        for(int i=0;i<nCasellePremio;i++){
            int nCaselleLibere = s.getSizeCaselleLibere();
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
