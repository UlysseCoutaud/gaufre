
package waffle.engine;

import java.awt.Point;
import java.util.Stack;
import waffle.gui.GuiController;
import waffle.util.Logger;

public class Engine {

    // Properties

    private Stack<GameState> undoStack;
    private Stack<GameState> redoStack;
    private GameState currentState;

    int currentPlayer = 1;

    // Constructors

    public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {
        this.undoStack = new Stack<GameState>();
        this.redoStack = new Stack<GameState>();
    }

    // Getters / setters

    public void setIHM(GuiController ihm) {
        // TODO
    }

    // Actions

    // TODO
    public void play(int x, int y) {
        GameState currentState = this.getCurrentGameState();
    }

    public void play(Point p) {
        play(p.x, p.y);
    }

    public GameState getCurrentGameState() {
        return currentState;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    // Undo / Redo

    public boolean isUndoable() {
        return !this.undoStack.isEmpty();
    }

    public boolean isRedoable() {
        return !this.redoStack.isEmpty();
    }

    public void undoAction() {
        if (!this.isUndoable()) {
            Logger.logEngine("No Actions left to undo.");
            return;
        }
        this.redoStack.push(this.currentState);
        this.currentState = this.undoStack.pop();
    }

    public void redoAction() {
        if (!this.isRedoable()) {
            Logger.logEngine("No actions to redo.");
            return;
        }
        this.undoStack.push(this.currentState);
        this.currentState = this.redoStack.pop();
    }

}
