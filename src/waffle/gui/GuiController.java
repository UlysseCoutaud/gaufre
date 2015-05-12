
package waffle.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import waffle.engine.Engine;

public class GuiController implements ComponentListener {

    // Properties

    private final static int defaultFrameWidth = 800;
    private final static int defaultFrameHeight = 800;
    private final static double partitionW = 7 / 10.; // Division horizontal de
                                                      // la fenetre
    private final static double partitionH1 = 1. / 10; // Division verticale de
                                                       // la fenetre haute
    private final static double partitionH2 = 5. / 10; // Division verticale de
                                                       // la fenetre basse

    private Engine engine;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private WaffleView waffleView;
    private JTextPane nameWindow;
    private JTextPane infoWindow;
    private ControlWindow controlWindow;
    private JSplitPane frameOrganizer1;
    private JSplitPane frameOrganizer2;
    private JSplitPane frameOrganizer3;

    // Constructors

    public GuiController(Engine engine) {
        this.engine = engine;
        this.setupMenuBar();
        this.setupFrame();
        this.frame.setVisible(true);
    }

    // Setup

    private JMenuItem createMenuItem(String title, String action) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(new ActionPerformer(this, action));
        return menuItem;
    }

    private void setupMenuBar() {
        this.menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Game");
        fileMenu.add(this.createMenuItem("New Game", "newGame"));
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
    }

    private void setupFrame() {
        int w = (int) (partitionW * defaultFrameWidth); // Largeur du panneau
                                                        // principal
        int h = defaultFrameHeight; // Hauteur du panneau principale
        int h1 = (int) (partitionH1 * h); // Hauteur du panneau lateral haut
        int h2 = (int) (partitionH2 * h); // Hauteur du panneau lateral centrale

        this.waffleView = new WaffleView(w, h, this.engine); // Initialisation
                                                             // du panneaux
                                                             // principal haut
        this.infoWindow = new JTextPane(); // Initialisation
        this.infoWindow.setSize(defaultFrameWidth - w, h1);
        // du
        // panneaux
        // lateral
        // centrale
        this.controlWindow = new ControlWindow(defaultFrameWidth - w, h - (h1 + h2), engine);// Initialisation
        // du
        // panneaux
        // lateral
        // bas

        this.resize(defaultFrameWidth, defaultFrameHeight);

        this.frame = new JFrame("Waffle game");
        this.frame.setSize(defaultFrameWidth, defaultFrameHeight);
        this.frame.setJMenuBar(this.menuBar);
        this.frame.add(frameOrganizer3);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Actions

    private void newGame() {

    }

    private void loadGame() {

    }

    private void exportGame() {

    }

    private void saveAndQuit() {

    }

    private void undo() {
        this.engine.undoAction();
    }

    private void redo() {
        this.engine.redoAction();
    }

    // Updating interface

    public void update() {
        this.undoMenuItem.setEnabled(this.engine.isUndoable());
        this.redoMenuItem.setEnabled(this.engine.isRedoable());
        // this.undoButton.setEnabled(this.engine.isUndoable());
        // this.redoButton.setEnabled(this.engine.isRedoable());
        this.waffleView.update();
    }

    // Resizing method

    @Override
    public void componentResized(ComponentEvent e) {
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        this.resize(width, height);
    }

    public void resize(int width, int height) {
        int w = (int) (partitionW * width); // Largeur du panneau principal
        int h = height; // Hauteur du panneau principale
        int h1 = (int) (partitionH1 * h); // Hauteur du panneau lateral haut
        int h2 = (int) (partitionH2 * h); // Hauteur du panneau lateral centrale

        this.waffleView.resize(w, h);
        this.frameOrganizer1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, infoWindow, controlWindow);
        this.frameOrganizer2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, nameWindow, frameOrganizer1);
        this.frameOrganizer3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, waffleView, frameOrganizer2);
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

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }
}
