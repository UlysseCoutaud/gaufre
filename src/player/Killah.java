package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import util.Logger;
import engine.GameState;
import engine.GameState.EdibleIterator;

public class Killah implements Player {
	private final Boolean nodeAND = true;
	private final Boolean nodeOR = false;
	private final Boolean killahPlay = true;
	private final Boolean opponentPlay = false;
	
	
	public class couplePointsPerdGagne {
		Boolean value;
		Point point;

		public couplePointsPerdGagne(Boolean val, Point p) {
			this.value = val;
			this.point = p;
		}
	}

	private class node {
		ArrayList<couplePointsPerdGagne> tablePerdGagne; // tables des choix
															// gagnants/perdants
		Boolean value; // Perd ou gagne
		Boolean type; // Noeud AND | OR
		Point choix; // n'a de sens que pour noeud OR
		@SuppressWarnings("unused")
		GameState configuration;

		public node(Boolean type, GameState myLocalConfiguration) {
			this.tablePerdGagne = new ArrayList<couplePointsPerdGagne>(10);
			this.configuration = myLocalConfiguration;
			this.type = type;
		}
		/*
		 * Calcule si le noeud est perdant ou gagnant en fonction de son type
		 * (noeud ET noeud OU)
		 */
		public void setValue() {
			Iterator<couplePointsPerdGagne> listIterator = this.tablePerdGagne
					.iterator();
			if (this.type == nodeAND) {
				this.value = true;
				while (listIterator.hasNext()) {
					this.value &= listIterator.next().value;
				}
			} else if (this.type == nodeOR) {
				this.value = false;
				while (listIterator.hasNext()) {
					this.value |= listIterator.next().value;
				}
			}
		}

		/*
		 * Renvoi la valeur du noeud (perdant ou gagnant pour killah)
		 */
		public Boolean getValue() {
			return this.value;
		}
		
		/*
		 * Choisi un noeud gagnant (avec aleatoire)
		 */
		public void setChoice() {
			Random rand = new Random();
			int tirage = rand.nextInt(this.tablePerdGagne.size());
			int i = 0;
			int j = 0;
			while (tirage > 0) {
				if (this.tablePerdGagne.get(j).value) {
					i = j;
				}
				j++;
				tirage--;
			}
			this.choix = this.tablePerdGagne.get(i).point;
		}
	}

	/*
	 * Envoi un choix parmi les noeuds gagnants
	 * A corriger: si pas de noeuds gagnants donner le noeuds le meilleur
	 */
	public node gaufreMinMax(GameState currentConfig, boolean joueur) {
		node currentNode;
		node sonNode;
		GameState bufferGameState;
		boolean nextPlayer = !joueur;
		// Cas de base
		if (currentConfig.mustLose() && (joueur == killahPlay)) {
			Logger.logIa("C");

			currentNode = new node(nodeAND, currentConfig);
			currentNode.tablePerdGagne.clear();
			currentNode.tablePerdGagne.add(new couplePointsPerdGagne(false,
					new Point(0, 0)));
		} else if (currentConfig.mustLose() && (joueur == opponentPlay)) {
			currentNode = new node(nodeOR, currentConfig);
			currentNode.tablePerdGagne.clear();
			currentNode.tablePerdGagne.add(new couplePointsPerdGagne(true,
					new Point(0, 0)));
			Logger.logIa("D");

		} else { // Cas appels recursifs
			Logger.logIa("B");

			// On prepare lers structures necessaires
			EdibleIterator eatable = currentConfig.getEdibleIterator();

			// Les appels recursifs:
			// Pour le noeud courant
			if (joueur == killahPlay) {
				currentNode = new node(nodeOR, currentConfig);
			} else { // if (joueur == opponentPlay)
				currentNode = new node(nodeAND, currentConfig);
			}
			// On explore tout les coups possibles
			while (eatable.hasNext()) {

				eatable.next();
				bufferGameState = currentConfig.cloneGameState();
				bufferGameState.eat(eatable.getPoint());
				sonNode = gaufreMinMax(bufferGameState, nextPlayer);
				currentNode.tablePerdGagne.add(new couplePointsPerdGagne(sonNode.getValue(), eatable.getPoint()));
				Logger.logIa("E");
			}
		}
		currentNode.setValue();
		currentNode.setChoice();
		currentNode.configuration = currentConfig.cloneGameState();
		return currentNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.Player#makeChoice(Board)
	 */
	@Override
	public Point makeChoice(GameState currentConfig) {
		Logger.logIa("A");
		return gaufreMinMax(currentConfig, killahPlay).choix;
	}

}