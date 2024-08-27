package game.sistema.varianti;

import game.sistema.SistemaImpl1;

public abstract class AbstractVariante implements Variante{

    private boolean activated;

    public void setActivated(boolean activated){this.activated=activated;}

    @Override
    public abstract void action(SistemaImpl1 s);
}
