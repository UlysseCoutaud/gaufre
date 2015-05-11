package gaufre;

import javax.swing.SwingUtilities;





public class Main
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
	public void run() 
	{
		IHM ihm;
		Engine engine;

		engine	= new Engine(defaultXDim, defaultYDim, defaultNbPlayers);
		ihm		= new IHM(engien);

		engine.setIHM(ihm);
	}
	public static void main(String [] args) 
	{
		System.out.println("Bienvenue dans notre GAUFRE!");
		SwingUtilities.invokeLater(new Main());
	}
}