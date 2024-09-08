package model.commands;

import model.SistemaImpl1;

public class TurnoCommand implements Command{

    private int turnoPrima, turnoDopo;
    private SistemaImpl1 s;

    public TurnoCommand(SistemaImpl1 s, int turnoPrima, int turnoDopo) {
        this.turnoPrima = turnoPrima;
        this.turnoDopo = turnoDopo;
        this.s = s;
    }


    @Override
    public boolean doIt() {
        s.setTurno(turnoDopo);
        return true;
    }

    @Override
    public boolean undoIt() {
        s.setTurno(turnoPrima);
        return true;
    }
}
