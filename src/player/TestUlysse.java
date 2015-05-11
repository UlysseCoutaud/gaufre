package player;

import engine.Board;

public class TestUlysse {

	public static void main(String[] args) {
		Board B = new Board(5, 5);
		Player D = new Dumb();

		for (int i = 1; i <= 1000; i++) {
			System.out.println(D.makeChoice(B));
		}

	}

}
