package game.sistema.commands;

public interface Command {
    boolean doIt();

    boolean undoIt();
}
