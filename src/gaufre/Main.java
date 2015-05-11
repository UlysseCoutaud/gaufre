package gaufre;

import ihm.GuiController;

import javax.swing.SwingUtilities;

import engine.Engine;

public class Main {
	// --------------------------------------------
	// Attributs:
	// --------------------------------------------
	private static final int defaultXDim = 10;
	private static final int defaultYDim = 10;

	private static final int defaultNbPlayers = 2;

public class Main implements Runnable
{
    // --------------------------------------------
    // Attributs:
    // --------------------------------------------
    private static final int defaultXDim		= 10;
    private static final int defaultYDim		= 10;

    private static final int defaultNbPlayers	= 2;

    // --------------------------------------------
    // Methodes principale:
    // --------------------------------------------
    @Override
    public void run() 
    {
	GuiController ihm;
	Engine engine;

	engine	= new Engine(defaultXDim, defaultYDim, defaultNbPlayers);
	ihm	= new GuiController(engine);

	engine.setIHM(ihm);
    }

    public static void main(String [] args) 
    {
	System.out.println("Bienvenue dans notre GAUFRE!");
	SwingUtilities.invokeLater(new Main());
    }
}