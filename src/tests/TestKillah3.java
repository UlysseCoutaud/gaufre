package tests;

import java.awt.Point;

import player.Killah;
import player.Medium;
import player.Player;
import engine.GameState;

public class TestKillah3 {

	public static void main(String[] args) {
		GameState B;
		Player P0, P1;
		Point p;
		int  k;
		String medium, killah, winner, name0, name1;
		medium = "Medium";
		killah = "Killah";


		
		name0 = medium;
		name1 = killah;
		P0 = new Medium();
		P1 = new Killah();

		System.out.println("|| "+name0 + " VS " + name1+" ||");

		B = new GameState(3,3);
		k=0;

		while (! B.mustLose()) {
			k++;
			if(k%2 == 0){
				System.out.println(name1);
				p = P1.makeChoice(B);
			}else{
				System.out.println(name0);

				p = P0.makeChoice(B);
			}
			B.eat(p);
			System.out.println("I choosed "+p+B);
		}
		
		// Who wins ?
		if(k%2 == 1) {
			winner = name1;
		} 
		else { 
			winner = name0;
		}
		System.out.println("winner="+winner);
		System.out.println("=====================");
		System.out.println("=====================");				

	}
}