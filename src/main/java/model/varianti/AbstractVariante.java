package model.varianti;

import model.SistemaImpl1;

public abstract class AbstractVariante implements Variante{

    protected boolean activated = false;


    @Override
    public boolean isActivated(){return activated;}

    @Override
    public abstract void action(SistemaImpl1 s);

    @Override
    public abstract void setActivated(boolean activated, SistemaImpl1 s);
}
