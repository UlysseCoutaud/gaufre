package engine;

import java.util.Stack;
import util.util;

public class Data {

	Stack<GameState> pastStack = new Stack<GameState>();
	Stack<GameState> futureStack = new Stack<GameState>();

	int currentPlayer = 1;

	public Data(int width, int height) {
		pastStack = new Stack<GameState>();
		futureStack = new Stack<GameState>();
	}

	public void undoAction() {
		if (pastStack.isEmpty()) {
			util.debug("undo Impossible");
			return;
		}
		saveOfStack.push(stack.pop());
	}

	public void redoAction() {
		if (saveOfStack.isEmpty()) {
			util.debug("redo impossible");
			return;
		}
		stack.push(SaveOfStack.pop());
	}

	public void addInStackAction(Square[][] squareBoard) {
		Board board = new Board(squareBoard, width, height);
		GameState gameState = new GameState(board, currentPlayer);
		if (currentPlayer == 1) {
			currentPlayer = 2;
		} else {
			currentPlayer = 1;
		}
		stack.push(gameState);
		saveOfStack.clear();
	}

	public erasePartOfWaffle(int x, int y) {
		if (board[x][y] == EMPTY) {
			util.debug("empty cell selected")
		} else if (board[x][y] == WAFFLE) {
			int xLeft = width - x;
			int yLeft = height - y;
			for (i = x; i <= xLeft; x++) {
				for (j = y; j <= yLeft; y++) {
					board[i][j] = EATEN;
				}
			}
		}
	}}
