package game.commands;

public interface Command {
    boolean doIt();

    boolean undoIt();
}
