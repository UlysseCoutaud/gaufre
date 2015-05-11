package player;

import java.awt.Point;
import java.util.Random;

/**
 * A little smarter than the dumb player :
 * Analyzes the next move. Avoids immediate defeat
 * and when victory is available, takes that choice
 */
public class Medium implements Player {

	@Override
	public Point makeChoice(Board currentConfig) {
		Point p;
		p = victoryNextTurn(currentConfig);
		do {
		p = randomPoint(currentConfig);
		} while (willLose(p));
		return p;
	}
	
	/* If there is a move leading to the adversary's 
	 * defeat next turn, returns that point.
	 * (ie. if all "safe" squares are inside a rectangle)
	 * Else returns null.
	 * */
	private Point victoryNextTurn(Board currentConfig) {
		Point p = null;
		
		return p;
	}
	
	/* Returns true if that choice lead to defeat
	 * in this turn or the next */
	private boolean willLose(Point p) {
		if()
		return false;
	}


	private Point randomPoint(Board currentConfig) {
		Random r = new Random();
		int x = r.nextInt(currentConfig.width);
		int y = r.nextInt(currentConfig.height);
		return new Point(x, y);
	}

}
