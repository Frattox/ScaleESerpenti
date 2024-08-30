package game.sistema.varianti;

import game.sistema.SistemaImpl1;

public abstract class AbstractVariante implements Variante{

    private boolean activated;

    @Override
    public void setActivated(boolean activated){this.activated=activated;}
    @Override
    public boolean isActivated(){return activated;}

    @Override
    public abstract void action(SistemaImpl1 s);
}
