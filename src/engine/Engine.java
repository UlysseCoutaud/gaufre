package engine;

import ihm.GuiController;

import java.awt.Point;
import java.util.Stack;

import util.util;

public class Engine {
	// pastStack contains present state as well
	private Stack<GameState> pastStack;
	private Stack<GameState> futureStack;
	private GameState currentState;

	int currentPlayer = 1;

	public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {

		pastStack = new Stack<GameState>();
		futureStack = new Stack<GameState>();
	}

	public void setIHM(GuiController ihm) {

	}

	public void undoAction() {
		if (!isUndoable()) {
			util.debug("No Actions left to undo.");
			return;
		}
		futureStack.push(currentState);
		currentState = pastStack.pop();
	}

	public void redoAction() {
		if (!isRedoable()) {
			util.debug("No actions to redo.");
			return;
		}
		pastStack.push(currentState);
		currentState = futureStack.pop();
	}

	public void play(int x, int y) {
		GameState currentState = getCurrentGameState(); // TODO
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

	public boolean isUndoable() {
		return !pastStack.isEmpty();
	}

	public boolean isRedoable() {
		return !futureStack.isEmpty();
	}
}
