package player;
import java.awt.Point;

/*
 * Interface player 
 */
public interface Player {
	/*
	 * Nom du player
	 */
	String name="Artifice";
	
	/*
	 * Le joueur joue: 
	 * prend la configuration courante en parametre
	 * retourne le choix du joueur sous forme de Point
	 */
	Point makeChoice(BoardUlysse currentConfig);
}
