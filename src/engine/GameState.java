package engine;

import java.awt.Point;

public class GameState {

	// Properties

	public enum Cell {
		EATEN, POISON, WAFFLE
	}

	public final Cell[][] board;
	public int currentPlayer;
	public final int width, height;

	// Constructors

	public GameState(Cell[][] board, int currentPlayer) {
		this.board = this.copyBoard(board);
		this.currentPlayer = currentPlayer;
		this.width = board.length;
		this.height = board[0].length;
	}

	public GameState(int width, int height) {
		this.board = this.generateNewBoard(width, height);
		this.width = width;
		this.height = height;
		this.currentPlayer = 1;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	// State of cells

	/**
	 * Returns true if the only square left is poisoned
	 */
	public boolean mustLose() {
		return height > 1 && isEaten(0, 1) && width > 1 && isEaten(1, 0);
	}

	public boolean isWaffle(int i, int j) {
		return board[i][j] == Cell.WAFFLE;
	}

	public boolean isWaffle(Point p) {
		return isWaffle(p.x, p.y);
	}

	public boolean isEaten(int i, int j) {
		return board[i][j] == Cell.EATEN;
	}

	public boolean isEaten(Point p) {
		return isEaten(p.x, p.y);
	}

	public boolean isPoison(int i, int j) {
		return board[i][j] == Cell.POISON;
	}

	public boolean isPoison(Point p) {
		return isPoison(p.x, p.y);
	}

	/**
	 * Returns true if we can eat this square without being poisoned
	 */
	public boolean isSafe(int i, int j) {
		return i != 0 || j != 0;
	}

	public Cell getCell(int i, int j) {
		return board[i][j];
	}

	/**
	 * Eats all the waffle squares to the right and under p
	 */
	public void eat(int x, int y) {
		for (int i = x; i < width; i++) {
			for (int j = y; j < height; j++) {
				if (isEaten(i, j))
					break;
				remove(i, j);
			}
		}
	}

	public void eat(Point p) {
		eat(p.x, p.y);
	}

	/**
	 * Removes one waffle square
	 */
	private void remove(int i, int j) {
		board[i][j] = Cell.EATEN;
	}

	/**
	 * Removes one waffle square
	 */
	public void remove(Point p) {
		board[p.x][p.y] = Cell.EATEN;
	}

	public GameState cloneGameState() {
		return new GameState(board, currentPlayer);
	}

	@Override
	public String toString() {
		String str = new String();
		str += "player " + currentPlayer + "'s turn.\n";
		str += this.boardToString(board);
		return str;
	}

	// Board helpers

	private Cell[][] generateNewBoard(int w, int h) {
		Cell[][] res = new Cell[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				res[i][j] = Cell.WAFFLE;
			}
		}
		res[0][0] = Cell.POISON;
		return res;
	}

	private Cell[][] copyBoard(Cell[][] board) {
		Cell[][] res = new Cell[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				res[i][j] = board[i][j];
			}
		}
		return res;
	}

	private String boardToString(Cell[][] board) {
		String string = new String();
		for (int i = 0; i < board.length; i++) {
			string += "+---";
		}
		string += "+\n";

		for (int i = 0; i < board.length; i++) {
			string += "| ";
			for (int j = 0; j < board[0].length; j++) {
				Cell cell = board[i][j];
				switch (cell) {
				case POISON:
					string += "P ";
					break;
				case WAFFLE:
					string += "W ";
					break;
				case EATEN:
					string += "  ";
					break;
				}
				string += "| ";
			}
			string += "\n";
			for (int k = 0; k < board.length; k++) {
				string += "+---";
			}
			string += "+\n";
		}
		return string;
	}

	// Iterators

	public EdibleIterator getEdibleIterator() {
		return new EdibleIterator();
	}

	public class EdibleIterator {

		private int x, y;
		private int nextX, nextY;

		private EdibleIterator() {
			x = 0;
			y = 0;
		}

		boolean hasNext() {
			for (int i = x; i < width; i++) {
				for (int j = y; j < height; j++) {
					if (isWaffle(i, j)) {
						nextX = i;
						nextY = j;
						return true;
					}
				}
			}
			return false;
		}

		Cell next() {
			x = nextX;
			y = nextY;
			return board[x][y];
		}

		int getX() {
			return x;
		}

		int getY() {
			return y;
		}

		Point getPoint() {
			return new Point(x, y);
		}
	}

}
