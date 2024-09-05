package game.sistema.varianti;

import game.sistema.Sistema;
import game.sistema.SistemaImpl1;

public interface Variante {
    void setActivated(boolean activated, SistemaImpl1 s);

    boolean isActivated();

    void action(SistemaImpl1 s);
}
