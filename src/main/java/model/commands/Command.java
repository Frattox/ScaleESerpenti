package model.commands;

public interface Command {
    boolean doIt();

    boolean undoIt();
}
