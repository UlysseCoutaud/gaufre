package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Engine;

@SuppressWarnings("serial")
public class ControlWindow extends JPanel {
	// --------------------------------------------
	// Attributs:
	// --------------------------------------------
	private final String undoText = "Undo";
	private final String redoText = "Redo";
	private final String restartText = "Restart";

	private Engine engine;
	private JButton undo;
	private JButton redo;
	private JButton restart;

	// --------------------------------------------
	// Constructeur:
	// --------------------------------------------
	public ControlWindow(int width, int height, Engine engine) {
		super();
		super.setSize(width, height);
		this.engine = engine;
		this.undo = new JButton(undoText);
		this.redo = new JButton(redoText);
		this.restart = new JButton(restartText);

		this.setLayout(new GridLayout(3, 1));

		this.add(undo);
		this.add(redo);
		this.add(restart);

		this.undo.addActionListener(new ActionPerformer(engine, "undo"));
		this.redo.addActionListener(new ActionPerformer(engine, "redo"));
		this.restart.addActionListener(new ActionPerformer(engine, "restart"));
	}

	// --------------------------------------------
	// Methodes locales:
	// --------------------------------------------
	public void update() {
		this.undo.setEnabled(engine.isUndoable());
		this.redo.setEnabled(engine.isRedoable());
		this.undo.setEnabled(engine.isUndoable() && engine.isUndoable());
	}
}