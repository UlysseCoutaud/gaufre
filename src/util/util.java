package util;

import engine.GameState.Cell;

public class util {
	public static void debug(String str) {
		System.out.println(str);
	}

	public static Cell[][] newBoard(int w, int h) {
		Cell[][] res = new Cell[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				res[i][j] = Cell.WAFFLE;
			}
		}
		res[0][0] = Cell.EATEN;
		return res;
	}

	public static String boardToString(Cell[][] board) {
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
}
