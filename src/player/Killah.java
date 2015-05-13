package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import util.Logger;
import engine.GameState;
import engine.GameState.EdibleIterator;

public class Killah implements Player {
	private final Boolean nodeAND = true;
	private final Boolean nodeOR = false;
	private final Boolean killahPlay = true;
	private final Boolean opponentPlay = false;


	public class donneesBranche {
		Boolean truthValue;
		Double choiceQuality;
		Point point;

		public donneesBranche(Boolean val, Point p, Double victoryProportion) {
			this.truthValue = val;
			this.point = p;
			this.choiceQuality = victoryProportion;
		}
	}

	private class node {
		ArrayList<donneesBranche> tablePerdGagne; // tables des choix
		// gagnants/perdants
		Boolean truthValue; // Perd ou gagne
		Boolean type; // Noeud AND | OR
		Point choix; // n'a de sens que pour noeud OR
		Double choiceQuality;
		@SuppressWarnings("unused")
		GameState configuration;

		public node(Boolean type, GameState myLocalConfiguration) {
			this.tablePerdGagne = new ArrayList<donneesBranche>(10);
			this.configuration = myLocalConfiguration;
			this.type = type;
		}
		/*
		 * Calcule si le noeud est perdant ou gagnant en fonction de son type
		 * (noeud ET noeud OU)
		 */
		public void setTruthValueandChoiceQuality() {
			Iterator<donneesBranche> listIterator = this.tablePerdGagne.iterator();
			Double choiceQuality = 0.0;
			donneesBranche donneesDeLaBranche;
			if (this.type == nodeAND) {
				this.truthValue = true;
				while (listIterator.hasNext()) {
					donneesDeLaBranche = listIterator.next();
					choiceQuality = Math.max(donneesDeLaBranche.choiceQuality,choiceQuality);
					this.truthValue = this.truthValue && donneesDeLaBranche.truthValue;
				}
			} else if (this.type == nodeOR) {
				this.truthValue = false;
				while (listIterator.hasNext()) {
					donneesDeLaBranche = listIterator.next();
					choiceQuality += donneesDeLaBranche.choiceQuality;
					this.truthValue = this.truthValue || donneesDeLaBranche.truthValue;
				}
				choiceQuality = (choiceQuality / this.tablePerdGagne.size());
			}
				this.choiceQuality = choiceQuality;
		}

		/*
		 * Renvoi la valeur du noeud (perdant ou gagnant pour killah)
		 */
		public Boolean getValue() {
			return this.truthValue;
		}
		public Double getQualityOfChoice(){
			return this.choiceQuality;
		}

		/*
		 * Choisi un noeud gagnant (avec aleatoire)
		 */
		public void setChoice() {
			Iterator<donneesBranche> listIterator = this.tablePerdGagne.iterator();
			Boolean winningNodeFound = false;
			donneesBranche branchData;
			while(listIterator.hasNext() && !winningNodeFound){
				branchData = listIterator.next();
				if (branchData.truthValue){
					winningNodeFound = true;
					this.choix = branchData.point;
					return;
				}
			};
			// Il n'y a pas de solution de victoire sure
			// On trouve la meilleure option, cad la branche avec la meilleure proportion de victoire
			listIterator = this.tablePerdGagne.iterator();
			donneesBranche bestBranch;
			
			bestBranch = new donneesBranche(false, new Point(2,3), 	0.0);
			while(listIterator.hasNext()){
				branchData = listIterator.next();
				if (branchData.choiceQuality>=bestBranch.choiceQuality){
					bestBranch = branchData;
				}
			}
			
			this.choix = bestBranch.point;
			return;
			//	Logger.logIa("le choix est"+this.choix.toString());
			/*Random rand = new Random();
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
		}*/
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
		if (currentConfig.mustLose() && (joueur == killahPlay)
		|| (currentConfig.boardIsEmpty() && (joueur == opponentPlay))) {
			//	Logger.logIa("C");
			currentNode = new node(nodeAND, currentConfig);
			currentNode.tablePerdGagne.clear();
			currentNode.tablePerdGagne.add(new donneesBranche(false, new Point(0,0), 0.0));
			currentNode.truthValue = false; 
			currentNode.choiceQuality = 0.0;
			Logger.logIa("On a la configuration"+currentConfig.toString()+"C'est a moi de jouer donc je perd");
		} else if ((currentConfig.mustLose() && (joueur == opponentPlay))
				|| (currentConfig.boardIsEmpty() && (joueur == killahPlay))) {
			currentNode = new node(nodeOR, currentConfig);
			currentNode.tablePerdGagne.clear();
			currentNode.tablePerdGagne.add(new donneesBranche(true, new Point(0,0), 1.0));
			currentNode.truthValue = true;
			currentNode.choiceQuality = 1.0;
	
			//	Logger.logIa("D");
			Logger.logIa("On a la configuration"+currentConfig.toString()+"C'est lui de jouer donc je gagne");

		} else { // Cas appels recursifs
			//Logger.logIa("B");
			//Logger.logIa("On a la configuration"+currentConfig.toString()+"appel recursif");

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
				//		Logger.logIa("Je mange"+eatable.getPoint().toString());
				bufferGameState = currentConfig.cloneGameState();
				bufferGameState.eat(eatable.getPoint());
				sonNode = gaufreMinMax(bufferGameState, nextPlayer);
		
				currentNode.tablePerdGagne.add(new donneesBranche(sonNode.getValue(), eatable.getPoint(), sonNode.getQualityOfChoice()));
			}
			currentNode.setTruthValueandChoiceQuality();
			Logger.logIa("On a la configuration"+currentConfig.toString()+" le noeud vaut "+ currentNode.getValue());
		}
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
		System.out.println("Je suis le killah je joue");
		return gaufreMinMax(currentConfig, killahPlay).choix;
	}

}

