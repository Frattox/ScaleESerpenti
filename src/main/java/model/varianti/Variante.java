package model.varianti;

import model.SistemaImpl1;

public interface Variante {
    void setActivated(boolean activated, SistemaImpl1 s);

    boolean isActivated();

    void action(SistemaImpl1 s);
}