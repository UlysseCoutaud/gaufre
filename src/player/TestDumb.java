package player;

import engine.GameState;

public class TestDumb {

	public static void main(String[] args) {
		// Board B = new Board(5, 5);
		// constructeur que j'ai fait juste pour vous
		// initialise un Board de départ
		// met le joueur courant à 1
		// ~ Val
		GameState gs = new GameState(5, 5);

		Player D = new Dumb();

		for (int i = 1; i <= 1000; i++) {
			System.out.println(i + ": " + D.makeChoice(gs));
		}

	}
}
