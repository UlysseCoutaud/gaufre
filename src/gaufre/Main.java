package gaufre;

import ihm.GuiController;

import java.io.IOException;

import javax.swing.SwingUtilities;

import engine.Engine;

public class Main implements Runnable {
	// --------------------------------------------
	// Attributs:
	// --------------------------------------------
	private static final int defaultXDim = 10;
	private static final int defaultYDim = 10;

	private static final int defaultNbPlayers = 2;

	// --------------------------------------------
	// Methodes principale:
	// --------------------------------------------
	@Override
	public void run() {
		GuiController ihm;
		Engine engine;

		int nbOfPlayers = defaultNbPlayers;

		engine = new Engine(defaultXDim, defaultYDim, defaultNbPlayers);
		try {
			ihm = new GuiController(engine);
			engine.setIHM(ihm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (nbOfPlayers == 0) {
			engine.startAIMatch();
		}
	}

	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre GAUFRE!");
		SwingUtilities.invokeLater(new Main());
	}
}