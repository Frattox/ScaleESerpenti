package game.sistema.commands;

import java.util.LinkedList;

public class HistoryCommandHandler implements CommandHandler{

    private int maxHistoryLenght;
    private LinkedList<Command> history;
    private LinkedList<Command> redoList;

    public HistoryCommandHandler(){
        this(100);
    }

    public HistoryCommandHandler(int maxHistoryLenght){
        if(maxHistoryLenght<0)
            throw new IllegalArgumentException();
        this.maxHistoryLenght = maxHistoryLenght;
    }

    @Override
    public void handle(Command cmd) {
        if(cmd.doIt())
            addToHistory(cmd);
        else
            history.clear();
        if(!redoList.isEmpty())
            redoList.clear();
    }

    public void redo(){
        if(!redoList.isEmpty()){
            Command redoCmd = redoList.removeLast();
            redoCmd.doIt();
            history.addFirst(redoCmd);
        }
    }

    public void undo(){
        if(!history.isEmpty()){
            Command undoCmd = history.removeLast();
            undoCmd.doIt();
            redoList.addFirst(undoCmd);
        }
    }

    private void addToHistory(Command cmd) {
        history.addFirst(cmd);
        if(history.size() > maxHistoryLenght){
            history.removeLast();
        }
    }
}
