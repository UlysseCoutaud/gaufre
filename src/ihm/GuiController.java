package ihm;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import engine.Engine;

public class GuiController implements ComponentListener {
	// --------------------------------------------
	// Attributs:
	// --------------------------------------------
	private final static String frameName = "Waffle Game";
	private final static int defaultFrameWidth = 800;
	private final static int defaultFrameHeight = 800;
	private final static double partitionW = 7 / 10.; // Division horizontal de
														// la fenetre
	private final static double partitionH1 = 1. / 10; // Division verticale de
														// la fenetre haute
	private final static double partitionH2 = 5. / 10; // Division verticale de
														// la fenetre basse
	private final static int secureH = 55;

	private JFrame frame; // Parametres graphiques
	private MainMenuBar menuBar;
	private WaffleView waffleView;
	private JTextPane nameWindow;
	private JTextPane infoWindow;
	private ControlWindow controlWindow;
	private JSplitPane frameOrganizer1;
	private JSplitPane frameOrganizer2;
	private JSplitPane frameOrganizer3;

	// --------------------------------------------
	// Constructeur:
	// --------------------------------------------
	public GuiController(int x, int y, Engine engine) {
		int w = (int) (partitionW * defaultFrameWidth); // Largeur du panneau
														// principal
		int h = defaultFrameHeight - secureH; // Hauteur du panneau principale
		int h1 = (int) (partitionH1 * h); // Hauteur du panneau lateral haut
		int h2 = (int) (partitionH2 * h); // Hauteur du panneau lateral centrale

		this.waffleView = new WaffleView(engine); // Initialisation du panneaux
													// principal
		this.nameWindow = new JTextPane(); // Initialisation du panneaux lateral
											// haut
		this.infoWindow = new JTextPane(); // Initialisation du panneaux lateral
											// centrale
		this.controlWindow = new ControlWindow(defaultFrameWidth - w, h
				- (h1 + h2), engine);// Initialisation du panneaux lateral bas
		this.menuBar = new MainMenuBar(this); // Initialisation du menuBar

		this.resize(defaultFrameWidth, defaultFrameHeight);

		this.frame = new JFrame(frameName);
		this.frame.setSize(defaultFrameWidth, defaultFrameHeight);
		this.frame.addComponentListener(this);
		this.frame.setJMenuBar(menuBar); // Placer l'ensemble des pan dans la
											// fenetre
		this.frame.add(frameOrganizer3);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}

	// --------------------------------------------
	// Methodes locales:
	// --------------------------------------------
	public void componentResized(ComponentEvent e) {
		int width = e.getComponent().getWidth();
		int height = e.getComponent().getHeight();
		this.resize(width, height);
	}

	public void resize(int width, int height) {
		int w = (int) (partitionW * width); // Largeur du panneau principal
		int h = height - secureH; // Hauteur du panneau principale
		int h1 = (int) (partitionH1 * h); // Hauteur du panneau lateral haut
		int h2 = (int) (partitionH2 * h); // Hauteur du panneau lateral centrale

		this.waffleView.setSize(w, h);
		this.frameOrganizer1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				infoWindow, controlWindow);
		this.frameOrganizer2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				nameWindow, frameOrganizer1);
		this.frameOrganizer3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, waffleView, frameOrganizer2);
		this.frameOrganizer1.setDividerLocation(h2); // Placer le separateur de
														// fenetres vertical
		this.frameOrganizer2.setDividerLocation(h1); // Placer le separateur de
														// fenetres vertical
		this.frameOrganizer3.setDividerLocation(w); // Placer le separateur de
													// fenetres horizontal
		this.frameOrganizer1.setDividerSize(12);
		this.frameOrganizer2.setDividerSize(12);
		this.frameOrganizer3.setDividerSize(12);
		this.frameOrganizer1.setEnabled(false);
		this.frameOrganizer2.setEnabled(false);
		this.frameOrganizer3.setEnabled(false);
	}

	public void componentHidden(ComponentEvent arg0) {
	}

	public void componentMoved(ComponentEvent arg0) {
	}

	public void componentShown(ComponentEvent arg0) {
	}

	public void updateYourself() {
		// TODO Auto-generated method stub
		// TODO c'est la methode appelee par le moteur, doit tout redessiner a
		// partir du current state
	}
}