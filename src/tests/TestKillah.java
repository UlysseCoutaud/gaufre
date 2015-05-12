package tests;

import player.Killah;
import player.Player;
import util.Logger;
import engine.GameState;

public class TestKillah {

	public static void main(String[] args) {
		GameState B = new GameState(3, 3);
		Player D = new Killah();
		Logger.enableIaLog = false ;
		for (int i = 1; i <= 5; i++) {
			System.out.println(i + ": " + D.makeChoice(B));
		}

	}
}
