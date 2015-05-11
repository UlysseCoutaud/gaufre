package engine;

import java.awt.Point;

public class Board {

	public class EatableIterator {

		private int x, y;
		private int nextX, nextY;
		private final Board board;

		private EatableIterator(Board b) {
			board = b;
			x = 0;
			y = 0;
		}

		boolean hasNext() {
			for (int i = x; i < board.width; i++) {
				for (int j = y; j < board.height; j++) {
					if (board.isWaffle(i, j)) {
						nextX = i;
						nextY = j;
						return true;
					}
				}
			}
			return false;
		}

		Square next() {
			x = nextX;
			y = nextY;
			return board.board[x][y];
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

	public enum Square {
		EATEN, POISON, WAFFLE
	}

	public final Square[][] board;
	public final int width;
	public final int height;

	public Board(int width, int height) {
		board = new Square[width][height];
		this.width = width;
		this.height = height;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = Square.WAFFLE;
			}
		}
	}

	public Board(int w, int h, Square[][] b) {
		width = w;
		height = h;
		board = new Square[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = b[i][j];
			}
		}
	}

	public EatableIterator getEatableIterator() {
		return new EatableIterator(this);
	}

	public boolean isWaffle(int i, int j) {
		return board[i][j] == Square.WAFFLE;
	}

	public boolean isWaffle(Point p) {
		return isWaffle(p.x, p.y);
	}

	public boolean isEaten(int i, int j) {
		return board[i][j] == Square.EATEN;
	}

	public boolean isEaten(Point p) {
		return isEaten(p.x, p.y);
	}

	public boolean isPoison(int i, int j) {
		return board[i][j] == Square.POISON;
	}

	public boolean isPoison(Point p) {
		return isPoison(p.x, p.y);
	}

	/**
	 * Returns true if we can eat this square without being poisoned
	 */
	public boolean isSafe(int i, int j) {
		return true;
	}

	/**
	 * Returns the position of the poisoned square in the waffle
	 */
	public Point poisonPosition() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (isPoison(i, j))
					return new Point(i, j);
			}
		}
		return null;
	}

	public Board cloneBoard() {
		return new Board(width, height, board);
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
					string += "E ";
				}
				string += "|";
			}
			string += "\n";
			for (i = 0; i < width; i++) {
				string += "+---";
			}
			string += "+\n";
		}
		return string;
	}

	/**
	 * Eats all the waffle squares to the right and under p
	 */
	public void eat(Point p) {// TODO
	}

	/**
	 * Removes one waffle square
	 */
	public void remove(Point p) {// TODO
	}
}
