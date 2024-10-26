package model.commands;

import java.util.LinkedList;

public class HistoryCommandHandler implements CommandHandler{

    private final int maxHistoryLenght;
    private final LinkedList<Command> history;
    private final LinkedList<Command> redoList;

    public HistoryCommandHandler(){
        this(100);
    }

    public HistoryCommandHandler(int maxHistoryLenght){
        history = new LinkedList<>();
        redoList = new LinkedList<>();
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

    public boolean redo(){
        if(!redoList.isEmpty()){
            Command redoCmd = redoList.removeFirst();
            if(redoCmd.doIt()) {
                history.addFirst(redoCmd);
            }
        }
        return !redoList.isEmpty();
    }

    public boolean undo(){
        if(!history.isEmpty()){
            Command undoCmd = history.removeFirst();
            if(undoCmd.undoIt()) {
                redoList.addFirst(undoCmd);
            }
        }
        return !history.isEmpty();
    }

    private void addToHistory(Command cmd) {
        history.addFirst(cmd);
        if(history.size() > maxHistoryLenght){
            history.removeLast();
        }
    }
}
