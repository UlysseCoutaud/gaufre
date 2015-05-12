package engine;

import ihm.GuiController;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import player.Medium;
import player.Player;
import util.util;

public class Engine {
	private Stack<GameState> pastStates;
	private Stack<GameState> futureStates;
	private GameState currentState;
	private ArrayList<Player> solveurList;
	private int nbOfHumanPlayers;
	private GuiController gui = null;

	int currentPlayer = 1;

	public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {

		pastStates = new Stack<GameState>();
		futureStates = new Stack<GameState>();
		currentState = new GameState(boardWidth, boardHeight);
		this.nbOfHumanPlayers = nbOfHumanPlayers;

		solveurList = new ArrayList<Player>();
		for (int i = nbOfHumanPlayers; i < 2; i++) {
			solveurList.add(new Medium()); // TODO faire en sorte qu'on puisse
											// choisir
		}
		util.debug(currentState.toString());
	}

	public void setIHM(GuiController ihm) {
		this.gui = ihm;
	}

	public void undoAction() {
		if (!isUndoable()) {
			util.debug("No Actions left to undo.");
			return;
		}
		futureStates.push(currentState);
		currentState = pastStates.pop();
	}

	public void redoAction() {
		if (!isRedoable()) {
			util.debug("No actions to redo.");
			return;
		}
		pastStates.push(currentState);
		currentState = futureStates.pop();
	}

	public void startAIMatch() {
		while (!currentState.mustLose()) {
			updateGuiIfAny();
			playCPU();
			passToNextPlayer();
		}
		currentPlayerDefeated();
	}

	private void updateGuiIfAny() {
		if (gui != null)
			gui.updateYourself();
	}

	// TODO
	public void play(int x, int y) {
		chooseCell(new Point(x, y));
		passToNextPlayer();
		updateGuiIfAny();
		checkForDefeat();

		if (isComputerPlayer(currentPlayer)) {
			playCPU();
			passToNextPlayer();
			updateGuiIfAny();
			checkForDefeat();
		}
	}

	private void passToNextPlayer() {
		currentPlayer %= 2;
		currentPlayer++;
	}

	private void checkForDefeat() {
		if (currentState.mustLose()) {
			currentPlayerDefeated();
		}
	}

	private void currentPlayerDefeated() {
		// TODO current player defaite
		util.debug("PLAYER " + currentPlayer + " DEFEATED!");
	}

	private void playCPU() {
		Player cpu = solveurList.get(currentPlayer - nbOfHumanPlayers - 1); // should
																			// always
																			// be
																			// 1
																			// but
																			// generic
																			// this
																			// way
		Point p = cpu.makeChoice(currentState);
		chooseCell(p);
	}

	private void chooseCell(Point p) {
		pastStates.push(currentState);
		currentState = currentState.cloneState();
		currentState.eat(p);
		util.debug(currentState.toString());
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
		return currentPlayer;
	}

	public boolean isUndoable() {
		return !pastStates.isEmpty();
	}

	public boolean isRedoable() {
		return !futureStates.isEmpty();
	}
}
