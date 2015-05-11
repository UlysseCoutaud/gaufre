package engine;

import java.awt.Point;

public class GameState {

	public enum Cell {
		EATEN, POISON, WAFFLE
	}

	final public Cell[][] board;
	final public int currentPlayer;
	public final int width, height;

	private Cell[][] copyOf(Cell[][] board) {
		Cell[][] res = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				res[i][j] = board[i][j];
			}
		}
		return res;
	}

	public GameState(int w, int h) {
		width = w;
		height = h;
		board = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = Cell.WAFFLE;
			}
		}
		currentPlayer = 1;
	}

	public GameState(Cell[][] board, int width, int height, int currentPlayer) {
		this.board = copyOf(board);
		this.currentPlayer = currentPlayer;
		this.width = width;
		this.height = height;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * Returns true if the only square left is poisoned
	 */
	public boolean mustLose() {
		return isEaten(0, 1) || isEaten(1, 0);
	}

	public GameState cloneState() {
		return new GameState(board, width, height, currentPlayer);
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

	public EdibleIterator getEdibleIterator() {
		return new EdibleIterator();
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

	@Override
	public String toString() {
		String string = new String();
		for (int i = 0; i < width; i++) {
			string += "+---";
		}
		string += "+\n";
		for (int i = 0; i < width; i++) {
			string += "| ";
			for (int j = 0; j < height; j++) {
				if (isPoison(i, j)) {
					string += "P ";
				} else if (isWaffle(i, j)) {
					string += "W ";
				} else {
					string += "  ";
				}
				string += "| ";
			}
			string += "\n";
			for (int k = 0; k < width; k++) {
				string += "+---";
			}
			string += "+\n";
		}
		return string;
	}

	/**
	 * Eats all the waffle squares to the right and under p
	 */
	public void eat(Point p) {
		for (int i = p.x; i < width; i++) {
			for (int j = p.y; j < height; j++) {
				remove(i, j);
			}
		}
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
}
