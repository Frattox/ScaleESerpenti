package model.commands;

import model.elementi.Casella;
import model.elementi.Pedina;

public class AvanzamentoCommand implements Command{

    private final Casella oldPos;
    private final Casella newPos;
    private final Pedina pedina;

    public AvanzamentoCommand(Casella oldPos, Casella newPos, Pedina pedina) {
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.pedina = pedina;
    }


    @Override
    public boolean doIt() {
        pedina.setPos(newPos);
        return true;
    }

    @Override
    public boolean undoIt() {
        pedina.setPos(oldPos);
        return true;
    }
}
