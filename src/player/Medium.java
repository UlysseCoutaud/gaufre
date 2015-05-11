package player;

import java.awt.Point;
import java.util.Random;

import engine.Board;

/**
 * A little smarter than the dumb eater : Analyzes the next move. Avoids
 * immediate defeat and when victory is available, takes that choice
 */
public class Medium implements Player {

	@Override
	public Point makeChoice(Board currentConfig) {
		Point p;
		Point loser = new Point(0, 0);

		p = searchVictoryNextTurn(currentConfig);
		if (p != null)
			return p; // Easy win

		Board nextConfig = currentConfig.cloneBoard();
		do { // Avoiding stupid choices
			p = randomPoint(currentConfig);
			if (p.equals(loser))
				break; // No other choice
			nextConfig.remove(p);
		} while (willLoseNextTurn(currentConfig, p));
		return p;
	}

	/*
	 * If all safe squares are inside a rectangle (and thus can be eaten in one
	 * bite) returns the top left corner of this rectangle. Else returns null.
	 */
	private Point searchVictoryNextTurn(Board currentConfig) {
		Point poison = currentConfig.poisonPosition();
		Point res = null;
		boolean wafflesDown = false; // wafflesDown = there are waffle squares
										// under the poison
		boolean wafflesRight = false; // wafflesRight = idem, but to the right
		int jStart;

		for (int i = 0; i < currentConfig.width; i++) {
			if (i > poison.x)
				jStart = 0;
			else
				jStart = poison.y + 1;
			/*
			 * We skip the squares to the top and left of the poison
			 */
			for (int j = jStart; j < currentConfig.height; j++) {
				if (currentConfig.isWaffle(i, j)) {
					if (res == null)
						res = new Point(i, j); // This is the first waffle
												// square we look at
					if (j > poison.y)
						wafflesDown = true;
					if (i > poison.x)
						wafflesRight = true;
				}
				if (wafflesDown & wafflesRight)
					return null; // The squares are not inside a "safe"
									// rectangle :(
			}
		}
		return res;
	}

	/*
	 * Returns true if that choice lead to defeat in this turn or the next
	 */
	private boolean willLoseNextTurn(Board currentConfig, Point p) {
		// Is this bite poisoned ?
		if (!currentConfig.isSafe(p.x, p.y))
			return true;

		// Will the opponent win if I choose this ?
		Point opponentChoice;
		Board nextConfig = (Board) currentConfig.cloneBoard();
		nextConfig.eat(p);
		opponentChoice = searchVictoryNextTurn(nextConfig);
		return opponentChoice != null;
	}

	/* Dumb point choice */
	private Point randomPoint(Board currentConfig) {
		Random r = new Random();
		int tirage = r.nextInt(currentConfig.width * currentConfig.height) + 1;
		int i = 0, j = 0;

		while (tirage > 0) {
			i = (i + 1) % currentConfig.width;
			if (i == 0) {
				j = (j + 1) % currentConfig.height;
			}
			if (currentConfig.isWaffle(i, j) || currentConfig.isPoison(i, j)) {
				tirage--;
			}
		}
		return new Point(i, j);
	}

}
