package player;

import java.awt.Point;
import java.util.Random;

public class Dumb implements Player {


	/*
	 * (non-Javadoc)
	 * @see player.Player#makeChoice(Board)
	 */
	@Override
	public Point makeChoice(BoardUlysse currentConfig) {
		Random r = new Random();
		int tirage = r.nextInt(currentConfig.width*currentConfig.height)+1;
		int i=0, j=0;

		while(tirage>0){
			i = (i+1) % currentConfig.width ;
			if ( i==0 ){
				j = (j+1) % currentConfig.height ;
			}
			if( BoardUlysse.isWaffle(i,j) || BoardUlysse.isPoison(i,j) ){
				tirage--;
			}
		}
		return new Point(i,j);
	}

}

