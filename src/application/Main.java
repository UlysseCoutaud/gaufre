package application;

import java.io.IOException;

import javax.swing.SwingUtilities;

import engine.Engine;
import gui.GuiController;
import util.Logger;

public class Main implements Runnable
{

	// Properties

    private static final int defaultXDim = 5;
    private static final int defaultYDim = 5;
    private static final int defaultNbOfPlayers = 2;

    private static int			nbOfPlayers;

	// Runnable

	@Override
	public void run() {
		Logger.logApp("App will launch");

        Engine engine = new Engine(defaultXDim, defaultYDim, nbOfPlayers);
        GuiController guiController = null;

        try						{guiController = new GuiController(engine);}
		catch (IOException e)	{e.printStackTrace(); System.exit(0);}
        engine.setIHM(guiController);

        if (nbOfPlayers == 0)	engine.startAIMatch();

		Logger.logApp("App did launch");
	}

    public static void main(String[] args)
    {
    	try					{nbOfPlayers = Integer.parseInt(args[0]) % 3;}
    	catch(Exception e)	{nbOfPlayers = defaultNbOfPlayers;}

    	SwingUtilities.invokeLater(new Main());
    }
}
