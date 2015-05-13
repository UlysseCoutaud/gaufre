package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.Timer;

import engine.Engine;
import engine.GameState;

public class GuiController implements ComponentListener
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private final static String				frameName			= "Waffle Game";
	private final static String				playerText			= "Player: ";
	private final static String				looseText			= "The looser is: " + playerText;
	private final static String				endInfoText			= "End of game!";
	private final static int				defaultFrameWidth	= 800;
	private final static int				defaultFrameHeight	= 800;
	private final static int				secureH				= 65;
	private final static double				partitionW			= 7/10.;	// Division horizontal de la fenetre
	private final static double				partitionH1			= 1./10;	// Division verticale de la fenetre haute
	private final static double				partitionH2			= 5./10;	// Division verticale de la fenetre basse

	private JFrame							frame;							// Parametres graphiques
	private JMenuBar						menuBar;
	private WaffleView						waffleView;
	private JTextPane						nameView;
	private JTextPane						infoView;
    private JMenuItem						undoMenuItem;
    private JMenuItem						redoMenuItem;
    private JMenuItem						restartMenuItem;
	private ControlWindow					controlWindow;
	private JSplitPane 						frameOrganizer1;
	private JSplitPane 						frameOrganizer2;
	private JSplitPane 						frameOrganizer3;
	private String[]						playersName = {"1", "2"};

	private Engine							engine;
	public boolean							isEnd;

// --------------------------------------------
// Constructeur:
// --------------------------------------------
	public GuiController(Engine engine) throws IOException
	{
		this.engine = engine;
		int w	= (int)(partitionW	* defaultFrameWidth);								// Largeur du panneau principal
		int h	= defaultFrameHeight - secureH;											// Hauteur du panneau principale
		int h1	= (int)(partitionH1	* h);												// Hauteur du panneau lateral haut
		int h2	= (int)(partitionH2	* h);												// Hauteur du panneau lateral centrale

		this.waffleView		= new WaffleView(w, h, engine);								// Initialisation du panneaux principal
		this.nameView		= new JTextPane();						 	  				// Initialisation du panneaux lateral haut
		this.infoView		= new JTextPane();											// Initialisation du panneaux lateral centrale
		this.controlWindow	= new ControlWindow(defaultFrameWidth-w, h-(h1+h2), engine, this);// Initialisation du panneaux lateral bas
		this.setupMenuBar(); 						          							// Initialisation du menuBar

		this.resize(defaultFrameWidth, defaultFrameHeight);

		this.frame = new JFrame(frameName);
		this.frame.setSize(defaultFrameWidth, defaultFrameHeight);
		this.frame.setJMenuBar(menuBar);												// Placer l'ensemble des pan dans la fenetre
		this.frame.add(frameOrganizer3);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		this.update();
	}

	public void update()
	{
		GameState gs = engine.getCurrentGameState();
		this.waffleView		.update();
		this.controlWindow	.update();
		this.nameView		.setText(playerText+ playersName[engine.getCurrentPlayer()-1]);
		this.updateMenuBar();
/*		switch(engine.getNbHumanPlayers())
		{
			case 0: infoView.setText(twoAIText);		break;
			case 1:
				String text = oneAIText;
				if (engine.getCurrentPlayer() == 1)	text += playerText+ engine.getCurrentPlayer();
				else								text += this.IAText;
				infoView.setText(text);
				break;
			case 2: infoView.setText(this.twoPlayersText);	break
			default: throw new RuntimeException("Undefined nbrPlayer value: " + gs.getNbPlayer());
		}
*/
		if (gs.mustLose() && gs.boardIsEmpty())
		{
			JOptionPane.showMessageDialog(null, looseText + playersName[engine.getCurrentPlayer()-1]);
			infoView.setText(endInfoText);
		}
	}
	public void setPlayersName(String name1, String name2)
	{
		this.playersName[0] = name1;
		this.playersName[1] = name2;
		this.nameView.setText(playerText+ playersName[engine.getCurrentPlayer()-1]);
	}
	public void setGroundDim(int x, int y)
	{
		if ((x <= 1) ||(y <= 1))	throw new RuntimeException("Wrong border dim: x = " + x + ". y = " + y);
		int width	= this.waffleView.getWidth();
		int height	= this.waffleView.getHeight();
		this.waffleView.resize(width, height);
	}

// --------------------------------------------
// Resizer:
// --------------------------------------------
	public void componentResized(ComponentEvent e)
	{
		int width 	= e.getComponent().getWidth();
		int height 	= e.getComponent().getHeight();
		this.resize(width, height);
	}

	private void resize(int width, int height)
	{
		int w = (int) (partitionW * width); 					// Largeur du panneau principal
		int h = height - secureH;								// Hauteur du panneau principale
		int h1 = (int) (partitionH1 * h);						// Hauteur du panneau lateral haut
		int h2 = (int) (partitionH2 * h);						// Hauteur du panneau lateral centrale

		this.waffleView.resize(w, h);
		this.frameOrganizer1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, infoView, controlWindow);
		this.frameOrganizer2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, nameView, frameOrganizer1);
		this.frameOrganizer3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, waffleView, frameOrganizer2);
		this.frameOrganizer1.setDividerLocation(h2);			// Placer le separateur de fenetres vertical
		this.frameOrganizer2.setDividerLocation(h1);			// Placer le separateur de fenetres vertical
		this.frameOrganizer3.setDividerLocation(w);				// Placer le separateur de fenetres horizontal
		this.frameOrganizer1.setDividerSize(12);
		this.frameOrganizer2.setDividerSize(12);
		this.frameOrganizer3.setDividerSize(12);
		this.frameOrganizer1.setEnabled(false);
		this.frameOrganizer2.setEnabled(false);
		this.frameOrganizer3.setEnabled(false);
	}

	public void componentHidden(ComponentEvent arg0)	{}
	public void componentMoved(ComponentEvent arg0)		{}
	public void componentShown(ComponentEvent arg0)		{}
// ---------------------------------------------
// Menu Bar
// ---------------------------------------------
	private JMenuItem createMenuItem(String title, String action)
	{
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.addActionListener(new ActionPerformer(this.engine, action));
		return menuItem;
	}

    private void setupMenuBar()
    {
        this.menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Game");
        this.restartMenuItem = this.createMenuItem("New Game", "newGame");
        this.restartMenuItem.addActionListener(new ActionPerformer(new RestartInterface(engine, this), "show"));
        fileMenu.add(this.restartMenuItem);
        fileMenu.add(this.createMenuItem("Load...", "loadGame"));
        fileMenu.add(this.createMenuItem("Export...", "exportGame"));
        fileMenu.add(this.createMenuItem("Quit", "saveAndQuit"));
        this.menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        this.undoMenuItem = this.createMenuItem("Undo", "undo");
        editMenu.add(this.undoMenuItem);
        this.redoMenuItem = this.createMenuItem("Redo", "redo");
        editMenu.add(this.redoMenuItem);
        this.menuBar.add(editMenu);
        updateMenuBar();
    }
    private void updateMenuBar()
    {
		this.undoMenuItem	.setEnabled(engine.isUndoable());
		this.redoMenuItem	.setEnabled(engine.isRedoable());
		this.restartMenuItem.setEnabled(engine.isUndoable() || engine.isRedoable());
    }
}