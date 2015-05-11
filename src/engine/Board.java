package engine;

import java.awt.Point;

public class Board {

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
		return this; // TODO
	}

	@Override
	public String toString() {
		return "TODO"; // TODO
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
