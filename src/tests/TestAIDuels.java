package tests;

import java.awt.Point;

import player.*;
import engine.GameState;

public class TestAIDuels {

	public static void main(String[] args) {
		GameState B;
		Player P0 = null, P1 = null;
		Point p;
		int win0, win1, winner = 0;
		String dumb, medium, killah, winnerName, name0 = null, name1 = null;
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
				for (k = 0; k <= 1000; k++) {
					if (B.mustLose()) {
						winner = (k+1)%2;
						break;
					}
					if (B.boardIsEmpty()) {
						winner = k%2;
						break;
					}
					if(k%2 == 0)
						p = P0.makeChoice(B);
					else
						p = P1.makeChoice(B);
					B.eat(p);
				}
				// Who wins ?
				if(winner == 0) {
					win0++;
					winnerName = name0;
				} 
				else { 
					win1++;
					winnerName = name1;
				}
				//System.out.println("\t" + winnerName + " wins");
				
			}
			// Total of the 50 games = result of the duel
			System.out.println("Total : " + win0 + "/" + win1);
			if (win0 == win1)
				System.out.println("Par");
			else {
				winnerName = win0 > win1 ? name0 : name1;
				System.out.println("The winner is " + winnerName);
			}

			System.out.println("===========================");
			System.out.println("===========================");
		}
	}

}
