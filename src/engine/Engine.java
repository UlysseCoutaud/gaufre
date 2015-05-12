
package engine;

import gui.GuiController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;
import player.Medium;
import player.Player;
import util.Logger;

public class Engine {

    private Stack<GameState> pastStates;
    private Stack<GameState> futureStates;
    private GameState currentState;
    private ArrayList<Player> solveurList;
    private int nbOfHumanPlayers;
    private GuiController gui = null;

    public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {
    	restart(boardWidth, boardHeight, nbOfHumanPlayers);
    }
    
    public void restart(int boardWidth, int boardHeight, int nbOfHumanPlayers) {
        pastStates = new Stack<GameState>();
        futureStates = new Stack<GameState>();
        currentState = new GameState(boardWidth, boardHeight);
        Logger.logEngine(currentState.toString());
        this.nbOfHumanPlayers = nbOfHumanPlayers;

        solveurList = new ArrayList<Player>();
        for (int i = nbOfHumanPlayers; i < 2; i++) {
            solveurList.add(new Medium()); // TODO faire en sorte qu'on puisse choisir
        }
    }

    public void setIHM(GuiController ihm) {
        this.gui = ihm;
    }

    public void startAIMatch() {
        while (!currentState.mustLose()) {
            updateGuiIfAny();
            playCPU();
        }
        currentPlayerDefeated();
    }

    public void play(int x, int y) {
        if (!this.currentState.isWaffle(x, y) ) {
            Logger.logEngine("Cell at " + x + " " + y + " can't be eaten.");
            return;
        }

        checkForDefeat();
        chooseCell(new Point(x, y));
        updateGuiIfAny();

        if (isComputerPlayer(getCurrentPlayer()) && !currentState.mustLose()) {
            checkForDefeat();
            playCPU();
            updateGuiIfAny();
        }
    }

    private void updateGuiIfAny() {
        if (gui == null)
            return;
        gui.update();
    }

    private void checkForDefeat() {
        if (currentState.mustLose()) {
            currentPlayerDefeated();
        }
    }

    private void currentPlayerDefeated() {
        Logger.logEngine("PLAYER " + getCurrentPlayer() + " DEFEATED");
    }

    private void playCPU() {
        Player cpu = solveurList.get(getCurrentPlayer() - nbOfHumanPlayers - 1);
        Point p;
        do {
            p = cpu.makeChoice(currentState);
        }
        while (this.currentState.isEaten(p));
        chooseCell(p);
    }

    private void chooseCell(Point p) {
        futureStates.clear();
        pastStates.push(currentState.cloneGameState());
        currentState = currentState.cloneGameState();
        currentState.currentPlayer %= 2;
        currentState.currentPlayer++;
        currentState.eat(p);
        Logger.logEngine(currentState.toString());
    }

    private boolean isComputerPlayer(int player) {
        return player > nbOfHumanPlayers;
    }

    public void play(Point p) {
        play(p.x, p.y);
    }

    public GameState getCurrentGameState() {
        return currentState;
    }

    public int getCurrentPlayer() {
        return currentState.currentPlayer;
    }

    // Undo / redo

    public void undo() {
        if (!isUndoable()) {
            Logger.logEngine("No Actions left to undo.");
            return;
        }
        futureStates.push(currentState.cloneGameState());
        currentState = pastStates.pop();
        gui.update();
        Logger.logEngine("ACTION UNDONE \n " + currentState.toString());
    }

    public void redo() {
        if (!isRedoable()) {
            Logger.logEngine("No actions to redo.");
            return;
        }
        pastStates.push(currentState.cloneGameState());
        currentState = futureStates.pop();
        gui.update();
        Logger.logEngine("ACTION REDONE \n " + currentState.toString());
    }

    public boolean isUndoable() {
        return !pastStates.isEmpty();
    }

    public boolean isRedoable() {
        return !futureStates.isEmpty();
    }

    public int getNbHumanPlayers() {
        return nbOfHumanPlayers;
    }
}
