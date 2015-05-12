package application;

import java.io.IOException;

import javax.swing.SwingUtilities;

import util.Logger;
import engine.Engine;
import gui.GuiController;

public class Main implements Runnable {

	// Properties

	private static final int defaultXDim = 10;
	private static final int defaultYDim = 10;
	private static final int defaultNbPlayers = 2;

	// Runnable

	@Override
	public void run() {
		Logger.logApp("App will launch");

		int nbOfPlayers = defaultNbPlayers;
		Engine engine = new Engine(defaultXDim, defaultYDim, nbOfPlayers);
		GuiController guiController;
		try {
			guiController = new GuiController(engine);
			engine.setIHM(guiController);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (nbOfPlayers == 0) {
			engine.startAIMatch();
		}

		Logger.logApp("App did launch");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}

}
