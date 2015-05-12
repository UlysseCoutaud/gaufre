package tests;

import java.awt.Point;

import player.Medium;
import player.Player;
import engine.GameState;

public class TestMedium {

	public static void main(String[] args) {
		GameState B = new GameState(5, 5);
		Player P = new Medium();
		Point p;

		System.out.println(B);
		System.out.println("=====================");
		for (int i = 1; i <= 100; i++) {
			if (B.mustLose())
				break;
			p = P.makeChoice(B);
			B.eat(p);
			System.out.println(i + ": (" + p.y + "," + p.x + ")");
			System.out.println(B);
			System.out.println("=====================");
		}

	}
}
