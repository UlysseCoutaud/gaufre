package ihm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;






@SuppressWarnings("serial")
public class ControlWindow extends JPanel
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private final String	undoText	= "Undo";
	private final String	redoText	= "Redo";
	private final String	restartText	= "Restart";

	public JButton	undo;
	public JButton	redo;
	public JButton	restart;

// --------------------------------------------
// Constructeur:
// --------------------------------------------
	public ControlWindow(int width, int height)
	{
		super();
		super.setSize(width, height);
		this.undo	= new JButton(undoText);
		this.redo	= new JButton(redoText);
		this.restart= new JButton(restartText);

		this.setLayout(new BorderLayout(3, 1));

		this.add(undo);
		this.add(redo);
		this.add(restart);
	}
}
