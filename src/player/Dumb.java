package player;

import java.awt.Point;
import java.util.Random;

import engine.GameState;

public class Dumb implements Player {

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.Player#makeChoice(GameState)
	 */
<<<<<<< HEAD
=======
	@Override
>>>>>>> branch 'master' of https://github.com/UlysseCoutaud/gaufre.git
	public Point makeChoice(GameState currentConfig) {
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
