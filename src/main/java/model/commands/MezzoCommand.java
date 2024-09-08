package model.commands;

import model.elementi.Pedina;
import model.elementi.Mezzi.Mezzo;

public class MezzoCommand implements Command{

    private final Mezzo m;
    private final Pedina p;

    public MezzoCommand(Mezzo m, Pedina p) {
        this.m = m;
        this.p = p;
    }


    @Override
    public boolean doIt() {
        p.setPos(m.getTo());
        return true;
    }

    @Override
    public boolean undoIt() {
        p.setPos(m.getFrom());
        return true;
    }
}
