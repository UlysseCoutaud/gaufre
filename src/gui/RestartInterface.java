package gui;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RestartInterface
{
// ---------------------------------
// Attributs
// ---------------------------------
	private static final String	frameName		= "Game parameters";
	private static final String	twoIAText		= "Two CPU players";
	private static final String	IAPlayer2Text	= "CPU: player 2";
	private static final String	twoPlayersText	= "Two players";
	private static final int	frameWidth		= 500;
	private static final int	frameHeight		= 500;
	private static final int	nbrElemX		= 15;
	private static final int	nbrElemY		= 2;
	private static final int	dimMin			= 2;
	private static final int	dimMax			= 15;	

	private JFrame				frame;
	private JTextField			player1Name;
	private JTextField			player2Name;
	private JComboBox<Integer>	dimXCombo;
	private JComboBox<Integer>	dimYCombo;
	private JRadioButton		twoIA;
	private JRadioButton		IAPlayer2;
	private JRadioButton		twoPlayers;
	private Integer[]			dimList;

// ---------------------------------
// Methodes locales
// ---------------------------------
	public void show()
	{
		GridLayout disposition = new GridLayout(nbrElemX, nbrElemY);

		this.frame = new JFrame(frameName);
		this.frame.setSize(300, 550);
		this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.frame.setLocation(450, 200);
		this.frame.setVisible(true);

		this.player1Name	= new JTextField();
		this.player2Name	= new JTextField();

		this.dimList = new Integer[dimMax - dimMin+1];
		for (int i=dimMin; i<=dimMax; i++) dimList[i-dimMin] = i;
		this.dimXCombo		= new JComboBox<Integer>(dimList);
		this.dimYCombo		= new JComboBox<Integer>(dimList);

		ButtonGroup bg	= new ButtonGroup();
		this.twoIA		= new JRadioButton(twoIAText);
		this.IAPlayer2	= new JRadioButton(twoIAText);
		this.twoPlayers	= new JRadioButton(twoIAText);
	}
}