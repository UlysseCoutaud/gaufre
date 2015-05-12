package engine;

import java.awt.Point;

import util.util;

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

	public GameState(Cell[][] board, int currentPlayer) {
		this.width = board.length;
		this.height = board[0].length;
		this.board = copyOf(board);
		this.currentPlayer = currentPlayer;
	}

	public GameState(int width, int height) {
		this.board = util.newBoard(width, height);
		this.width = width;
		this.height = height;
		this.currentPlayer = 1;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * Returns true if the only square left is poisoned
	 */
	// public boolean mustLose() {
	// return isEaten(0, 1) || isEaten(1, 0);
	// }
	// qui a fait ÇA?!

	public boolean mustLose() {
		return isEaten(0, 1) && isEaten(1, 0); // non mais
	}

	public GameState cloneState() {
		return new GameState(board, currentPlayer);
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

	/**
	 * Eats all the waffle squares to the right and under p
	 */
	public void eat(int x, int y) {
		for (int i = x; i < width; i++) {
			for (int j = y; j < height; j++) {
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

	@Override
	public String toString() {
		String str = new String();
		str += "player " + currentPlayer + "'s turn.\n";
		str += util.boardToString(board);
		return str;
	}
}
