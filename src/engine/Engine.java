
package engine;

import gui.GuiController;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.Timer;

import player.Dumb;
import player.Killah;
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
    private boolean isWaitingIa = false;
    private int waitingDelay = 0;

    public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {
    	newGame(boardWidth, boardHeight, nbOfHumanPlayers);
    }

    public void newGame(int boardWidth, int boardHeight, int nbOfHumanPlayers) {
        pastStates = new Stack<GameState>();
        futureStates = new Stack<GameState>();
        currentState = new GameState(boardWidth, boardHeight);
        Logger.logEngine(currentState.toString());
        this.nbOfHumanPlayers = nbOfHumanPlayers;

        solveurList = new ArrayList<Player>();
    }

    public void setIHM(GuiController ihm) {
        this.gui = ihm;
    }
    
    public void addEasyAI() {
    	solveurList.add(new Dumb());
    }
    
    public void addMediumAI() {
    	solveurList.add(new Medium());
    }
    
    public void addHardAI() {
    	solveurList.add(new Killah());
    }

    public void startAIMatch() {
    	this.isWaitingIa = true;
    	if (!currentState.mustLose() && !currentState.boardIsEmpty()) {
			ActionListener taskPerformer = new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			playCPU();
	    			updateGuiIfAny();
	    			startAIMatch();
	    		}
	    	};
	    	Timer timer = new Timer(this.waitingDelay, taskPerformer);
	    	timer.setRepeats(false);
	    	timer.start();
			
		} else {
	    	currentPlayerDefeated();
	       	isWaitingIa = false;
		}
    }

    public void play(int x, int y) {
    	if (this.isWaitingIa) {
    		Logger.logEngine("We are waiting the AI");
    		return;
    	}
        if (!this.currentState.isWaffle(x, y) ) {
            Logger.logEngine("Cell at " + x + " " + y + " can't be eaten.");
            return;
        }

        checkForDefeat();
        chooseCell(new Point(x, y));
        updateGuiIfAny();

        if (isComputerPlayer(getCurrentPlayer()) && !currentState.mustLose()) {
        	this.isWaitingIa = true;
        	
        	ActionListener taskPerformer = new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			checkForDefeat();
        			playCPU();
        			updateGuiIfAny();
        			isWaitingIa = false;
        		}
        	};
        	Timer timer = new Timer(this.waitingDelay, taskPerformer);
        	timer.setRepeats(false);
        	timer.start();
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
    
    public int getWaitingDelay() {
    	return this.waitingDelay;
    }
    
    public void setWaitingDelay(int delay) {
    	this.waitingDelay = delay;
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
        if(nbOfHumanPlayers == 1) {
        	if(currentState.currentPlayer == 2) undo();
        }
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
        if(nbOfHumanPlayers == 1) {
        	if(currentState.currentPlayer == 2) redo();
        }
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

	public void printPast() {
		Iterator<GameState> it = pastStates.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString() + "\n\n");
		}
	}
}
