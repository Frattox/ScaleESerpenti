package game.sistema.commands;

import game.sistema.SistemaImpl1;

public class DadiCommand implements Command{

    private final SistemaImpl1 s;
    private final int lancioPrima, lancioDopo;

    public DadiCommand(SistemaImpl1 s,int lancioPrima, int lancioDopo){
        this.s = s;
        this.lancioPrima=lancioPrima;
        this.lancioDopo=lancioDopo;
    }

    @Override
    public boolean doIt() {
        s.setLancio(lancioDopo);
        return true;
    }

    @Override
    public boolean undoIt() {
        s.setLancio(lancioPrima);
        return true;
    }
}
