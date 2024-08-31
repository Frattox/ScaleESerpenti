package game.sistema.varianti;

import game.sistema.SistemaImpl1;

//SINGLETON
public class VarianteCaselleSosta extends AbstractVariante{

    private static Variante varianteCaselleSosta;

    private VarianteCaselleSosta(){}

    public static Variante getInstance(){
        if(varianteCaselleSosta==null)
            varianteCaselleSosta = new VarianteCaselleSosta();
        return varianteCaselleSosta;
    }

    @Override
    public void action(SistemaImpl1 s) {
        int nCaselleSosta = s.getnCaselleSosta();

    }
}
