package game.sistema.commands;

import game.sistema.Mattaro.Carta;
import game.sistema.SistemaImpl1;

public class CartaCommand implements Command{

    private final Carta cartaPrima, cartaDopo;
    private final SistemaImpl1 s;

    public CartaCommand(SistemaImpl1 s, Carta cartaPrima, Carta cartaDopo) {
        this.cartaPrima = cartaPrima;
        this.cartaDopo = cartaDopo;
        this.s = s;
    }

    @Override
    public boolean doIt() {
        s.setCartaPescata(cartaDopo);
        return true;
    }

    @Override
    public boolean undoIt() {
        s.setCartaPescata(cartaPrima);
        return true;
    }
}
