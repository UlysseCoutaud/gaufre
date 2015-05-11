package player;

import java.awt.Point;
import java.util.Random;

public class Dumb implements Player {

	@Override
	public Point makeChoice(Board currentConfig) {
		Random r = new Random();
		int tirage = r.nextInt(currentConfig.width*currentConfig.height);
		
		
		return new Point(0,0);
	}
	
}

