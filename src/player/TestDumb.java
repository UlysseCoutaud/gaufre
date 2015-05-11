package player;

import engine.GameState;

public class TestDumb {

	public static void main(String[] args) {
		GameState B = new GameState(5, 5);
		Player D = new Dumb();

		for (int i = 1; i <= 1000; i++) {
			System.out.println(i + ": " + D.makeChoice(B));
		}

	}
}
