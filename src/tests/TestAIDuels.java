package tests;

import player.Dumb;
import player.Medium;
import player.Player;
import engine.GameState;

public class TestAIDuels {

	public static void main(String[] args) {
		GameState B;
		Player P1, P2;
		int win1, win2;
		String dumb, medium, killah, winner, name1, name2;
		dumb = "Dumb";
		medium = "Medium";
		killah = "Killah";

		for (int i = 0; i < 5; i++) {
			switch (i) {
			case 0:
				name1 = dumb;
				name2 = medium;
				P1 = new Dumb();
				P2 = new Medium();
				break;
			case 1:
				name1 = medium;
				name2 = dumb;
				P1 = new Medium();
				P2 = new Dumb();
				break;
			case 2:
				name1 = dumb;
				name2 = killah;
				P1 = new Dumb();
				// P2 = new Killah();
				break;
			case 3:
				name1 = killah;
				name2 = dumb;
				// P1 = new Killah();
				P2 = new Dumb();
				break;
			case 4:
				name1 = medium;
				name2 = killah;
				P1 = new Medium();
				// P2 = new Killah();
				break;
			default:
				name1 = killah;
				name2 = medium;
				// P1 = new Killah();
				P2 = new Medium();
				break;
			}
			B = new GameState(5, 5);
			win1 = win2 = 0;
			System.out.println(name1 + " VS " + name2);
			for (int j = 0; j < 20; j++) {
				// TODO : New games
				/*
				 * if(player1 wins) { win1++; winner = name1; } else { win2++;
				 * winner = name2; } System.out.println(winner + " wins");
				 */
			}
			winner = win1 > win2 ? name1 : name2;
			System.out.println("Total : " + win1 + "/" + win2);
			if (win1 == win2)
				System.out.println("Par");
			else
				System.out.println("The winner is " + winner);

			System.out.println("===========================");
			System.out.println("===========================");
		}
	}

}
