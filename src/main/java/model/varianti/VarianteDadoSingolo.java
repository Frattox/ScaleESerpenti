package model.varianti;

import model.elementi.Dado;
import model.sistema.SistemaImpl1;

import java.util.List;

public class VarianteDadoSingolo extends AbstractVariante{

    public VarianteDadoSingolo(){}

    @Override
    public void setActivated(boolean activated, SistemaImpl1 s) {this.activated=activated;}

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
