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
	private GuiController gui;

	int currentPlayer = 1;

	public Engine(int boardWidth, int boardHeight, int nbOfHumanPlayers) {

		pastStates = new Stack<GameState>();
		futureStates = new Stack<GameState>();
		currentState = new GameState(boardHeight, boardWidth);
		this.nbOfHumanPlayers = nbOfHumanPlayers;

		solveurList = new ArrayList<Player>();
		for (int i = nbOfHumanPlayers; i < 2; i++) {
			solveurList.add(new Medium()); // TODO faire en sorte qu'on puisse
											// choisir
		}
	}

	public void setIHM(GuiController ihm) {
		this.gui = ihm;
	}

	public void startAIMatch() {
		while (!currentState.mustLose()) {
			gui.update();
			playCPU();
			currentPlayer++;
		}
		currentPlayerDefeated();
	}

	// TODO
	public void play(int x, int y) {
		chooseCell(new Point(x, y));
		currentPlayer++;
		gui.update();
		checkForDefeat();

		if (isComputerPlayer(currentPlayer)) {
			playCPU();
			currentPlayer++;
			gui.update();
			checkForDefeat();
		}
	}

	private void checkForDefeat() {
		if (currentState.mustLose()) {
			currentPlayerDefeated();
		}
	}

	private void currentPlayerDefeated() {
		// TODO current player defaite
		// Affichage console vite fait -- Julie
		System.out.println("player " + currentPlayer + " defeated");
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
		currentState = currentState.cloneGameState();
		currentState.eat(p);
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

	// Undo / redo

	public void undoAction() {
		if (!isUndoable()) {
			Logger.logEngine("No Actions left to undo.");
			return;
		}
		futureStates.push(currentState);
		currentState = pastStates.pop();
	}

	public void redoAction() {
		if (!isRedoable()) {
			Logger.logEngine("No actions to redo.");
			return;
		}
		pastStates.push(currentState);
		currentState = futureStates.pop();
	}

	public boolean isUndoable() {
		return !pastStates.isEmpty();
	}

	public boolean isRedoable() {
		return !futureStates.isEmpty();
	}
}
