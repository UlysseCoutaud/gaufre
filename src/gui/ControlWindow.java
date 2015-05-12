
package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Engine;

public class ControlWindow extends JPanel {

    // Properties

    private static final long serialVersionUID = 1L;

    public JButton undoButton;
    public JButton redoButton;

    // Constructors

    public ControlWindow(int width, int height, Engine engine) {
        super.setSize(width, height);

        this.undoButton = new JButton("Undo");
        this.redoButton = new JButton("Redo");
        JButton newGameButton = new JButton("New game");

        this.setLayout(new GridLayout(3, 1));
        this.add(this.undoButton, 0);
        this.add(this.redoButton, 1);
        this.add(newGameButton, 2);

        this.undoButton.addActionListener(new ActionPerformer(engine, "undo"));
        this.redoButton.addActionListener(new ActionPerformer(engine, "redo"));
        newGameButton.addActionListener(new ActionPerformer(engine, "newGame"));
    }

}
