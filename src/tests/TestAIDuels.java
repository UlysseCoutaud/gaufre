package tests;

import java.awt.Point;

import player.Dumb;
import player.Medium;
import player.Player;
import engine.GameState;

public class TestAIDuels {

	public static void main(String[] args) {
		GameState B;
		Player P1, P2;
		Point choice1, choice2;
		int win1, win2;
		String dumb, medium, killah, winner, name1, name2;
		dumb = "Dumb";
		medium = "Medium";
		killah = "Killah";

		for (int i = 0; i < 2; i++) {
			switch (i) {
			case 0:
				name1 = dumb;
				name2 = medium;
				P1 = new Dumb();
				P2 = new Medium();
				break;
			case 1:
				name1 = dumb;
				name2 = killah;
				P1 = new Dumb();
				// P2 = new Killah();
				break;
			default:
				name1 = medium;
				name2 = killah;
				P1 = new Medium();
				// P2 = new Killah();
				break;
			}
			B = new GameState(5, 5);
			win1 = win2 = 0;
			System.out.println(name1 + " VS " + name2);
			for (int j = 0; j < 20; j++) {
				// TODO : New games
			}
			winner = win1 > win2 ? name1 : name2;
			if (win1 == win2)
				System.out.println("Par");
			else
				System.out.println("The winner is " + winner);
		}
	}

}
