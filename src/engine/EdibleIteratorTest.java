package engine;

import engine.GameState.EdibleIterator;

public class EdibleIteratorTest {

	public static void main(String[] args) {
		GameState gs = new GameState(10,10);
		System.out.println(gs.toString());
		gs.eat(3,0);
		gs.eat(2,2);
		gs.eat(0,3);
		System.out.println(gs.toString());
		
		EdibleIterator it = gs.getEdibleIterator();
		
		while(it.hasNext()) {
			it.next();
			System.out.println(it.getPoint());
		}
	}
}