package player;

import java.awt.Point;

import engine.Board;

public class TestMedium {

	public static void main(String[] args) {
		Board B = new Board(5, 5);
		Player P = new Medium();
		Point p;

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
