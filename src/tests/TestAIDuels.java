package tests;

import java.awt.Point;

import player.*;
import engine.GameState;

public class TestAIDuels {

	public static void main(String[] args) {
		GameState B;
		Player P0 = null, P1 = null;
		Point p;
		int win0, win1;
		String dumb, medium, killah, winner, name0 = null, name1 = null;
		dumb = "Easy";
		medium = "Medium";
		killah = "Difficult";

		/* We do 6 duels in total :
		 * Each of the 3 players fights against the 2 others
		 * They first begin the matches, then play second
		 */
		for (int i = 0; i < 6; i++) {
			switch (i) {
			case 0:
				name0 = dumb;
				P0 = new Dumb();
				name1 = medium;
				P1 = new Medium();
				break;
			case 1:
				name0 = medium;
				P0 = new Medium();
				name1 = dumb;
				P1 = new Dumb();
				break;
			case 2:
				name0 = dumb;
				P0 = new Dumb();
				name1 = killah;
				P1 = new Killah();
				break;
			case 3:
				name0 = killah;
				P0 = new Killah();
				name1 = dumb;
				P1 = new Dumb();
				break;
			case 4:
				name0 = medium;
				P0 = new Medium();
				name1 = killah;
				P1 = new Killah();
				break;
			case 5:
				name0 = killah;
				P0 = new Killah();
				name1 = medium;
				P1 = new Medium();
				break;
			}
			win0 = win1 = 0;
			System.out.println("|| "+name0 + " VS " + name1+" ||");
			// Each duel consists of 50 games
			for (int j = 0; j < 50; j++) {
				int k;
				// New game (we go up to 1000 moves)
				B = new GameState(3, 5);
				for (k = 1; k <= 1000; k++) {
					if (B.mustLose())
						break;
					if(k%2 == 0)
						p = P0.makeChoice(B);
					else
						p = P1.makeChoice(B);
					B.eat(p);
				}
				// Who wins ?
				if(k%2 == 1) {
					win0++;
					winner = name0;
				} 
				else { 
					win1++;
					winner = name1;
				}
				//System.out.println(winner + " wins");
				
			}
			// Total of the 50 games = result of the duel
			System.out.println("Total : " + win0 + "/" + win1);
			if (win0 == win1)
				System.out.println("Par");
			else {
				winner = win0 > win1 ? name0 : name1;
				System.out.println("The winner is " + winner);
			}

			System.out.println("===========================");
			System.out.println("===========================");
		}
	}

}
