package player;

import java.awt.Point;
import java.util.Random;

import engine.Board;

/**
 * A little smarter than the dumb player : Analyzes the next move.
 */
public class Medium implements Player {

	public Point makeChoice(Board currentConfig) {
		Point p;

		p = searchVictoryNextTurn(currentConfig);
		if (p != null) {
			System.out.println("Win");
			return p; // Easy win
		}

		Board nextConfig = currentConfig.cloneBoard();
		do { // Avoiding stupid choices
			if (mustLose(nextConfig)) { // Only the poison is remaining
				System.out.println("Lose");
				p = new Point(0, 0);
				break;
			}
			p = randomPoint(nextConfig);
			nextConfig.remove(p); // We don't want to repeat the same choices
		} while (willLoseNextTurn(currentConfig, p));
		return p;
	}

	/*
	 * Returns true if the only square left is poisoned
	 */
	private boolean mustLose(Board cf) {
		for (int i = 0; i < cf.width; i++) {
			for (int j = 0; j < cf.height; j++) {
				if (cf.isWaffle(i, j))
					return false;
			}
		}
		return true;
	}

	/*
	 * If all safe squares are inside a rectangle (and thus can be eaten in one
	 * bite) returns the top left corner of this rectangle. Else returns null.
	 */
	private Point searchVictoryNextTurn(Board currentConfig) {
		Point poison = new Point(0, 0);
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

	/*
	 * Dumb point choice
	 */
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
