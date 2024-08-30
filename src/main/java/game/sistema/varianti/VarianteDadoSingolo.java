package game.sistema.varianti;

import elementi.Dado;
import game.sistema.Sistema;
import game.sistema.SistemaImpl1;

import java.util.List;

//SINGLETON
public class VarianteDadoSingolo extends AbstractVariante{

    private VarianteDadoSingolo(){}

    public static Variante getInstance(){return new VarianteDadoSingolo();}

    @Override
    public void action(SistemaImpl1 s) {
        List<Dado> dadi = s.getDadi();
        dadi.add(new Dado());
        //controllo
        if(!this.isActivated()){
            //azione
            dadi.add(new Dado());
        }
    }
}