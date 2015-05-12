package player;

import java.awt.Point;

import engine.GameState;

public class TestMedium {

	public static void main(String[] args) {
		GameState B = new GameState(5, 5);
		Player P = new Medium();
		Point p;

		// bon ici je sais pas trop ce que vous voulez faire, y'a une methode
		// GameState.toString() si vous voulez...
		// ~ Val
		System.out.println(B);
		System.out.println("=====================");
		for (int i = 1; i <= 10; i++) {
			p = P.makeChoice(B);
			B.eat(p);
			System.out.println(i + ": (" + p.y + "," + p.x + ")");
			System.out.println(B);
			System.out.println("=====================");
		}

	}
}
