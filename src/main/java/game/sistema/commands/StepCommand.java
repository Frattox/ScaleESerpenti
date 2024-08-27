package game.sistema.commands;

import elementi.Casella;
import elementi.Pedina;

public class StepCommand implements Command{

    private final Casella oldPos;
    private final Casella newPos;
    private final Pedina pedina;

    public StepCommand(Casella oldPos, Casella newPos, Pedina pedina) {
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
