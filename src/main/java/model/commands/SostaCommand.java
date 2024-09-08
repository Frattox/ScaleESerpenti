package model.commands;

import model.elementi.Pedina;

public class SostaCommand implements Command{

    private final Pedina p;
    private final int sosta;

    public SostaCommand(Pedina p, int sosta){
        this.p = p;
        this.sosta = sosta;
    }

    @Override
    public boolean doIt() {
        p.setSosta(sosta);
        return true;
    }

    @Override
    public boolean undoIt() {
        p.setSosta(0);
        return true;
    }
}
