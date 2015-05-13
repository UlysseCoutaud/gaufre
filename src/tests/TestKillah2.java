package tests;

import java.awt.Point;

import player.Killah;
import player.Player;
import util.Logger;
import engine.GameState;

public class TestKillah2 {

	public static void main(String[] args) {
		Logger.enableIaLog = false ;
		GameState B = new GameState(6, 7);
		Player P = new Killah();
		Point p;
		//System.out.println(B);
		System.out.println("=====================");
		System.out.println("=====================");
		for (int i = 1; i <= 100; i++) {
			if (B.mustLose())
				break;
			p = P.makeChoice(B);
			B.eat(p);
			System.out.println(i + ": (" + p.y + "," + p.x + ")");
			System.out.println(B);
			System.out.println("=====================");
			System.out.println("=====================");
		}

	}
}