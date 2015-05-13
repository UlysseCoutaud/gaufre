package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import engine.Engine;

public class RestartInterface
{
// ---------------------------------
// Attributs
// ---------------------------------
	private static final String		frameName		= "Game parameters";
	private static final String		twoIAText		= "Two CPU players";
	private static final String		IAPlayer2Text	= "CPU: player 2";
	private static final String		twoPlayersText	= "Two players";
	private static final String		player1Text		= "Player1";
	private static final String		player2Text		= "Player2";
	private static final String		dimXText		= "Board width";
	private static final String		dimYText		= "Board height";
	private static final String		IALevelText		= "CPU Level";
	private static final String		playersTypeText	= "Players type";
	private static final String[]	IALevelName		= {"Dumb", "Medium", "Killah"};
	private static final int		frameWidth		= 300;
	private static final int		frameHeight		= 500;
	private static final int		nbrElemX		= 15;
	private static final int		nbrElemY		= 2;
	private static final int		dimMin			= 2;
	private static final int		dimMax			= 15;	

	private JFrame					frame;
	private JTextField				player1Name;
	private JTextField				player2Name;
	private JComboBox<Integer>		dimXCombo;
	private JComboBox<Integer>		dimYCombo;
	private JComboBox<String>		IALevelCombo;
	private JRadioButton			twoIA;
	private JRadioButton			IAPlayer2;
	private JRadioButton			twoPlayers;
	private JButton					ok;
	private JButton					cancel;
	private Integer[]				dimList;

	private Engine					engine;
	private GuiController			gui;

// ---------------------------------
// Constructeur
// ---------------------------------
	public RestartInterface(Engine en, GuiController gui)
	{
		if (frame != null) return;

		this.engine	= en;
		this.gui	= gui;
	}

// ---------------------------------
// Methodes locales
// ---------------------------------
	public void show()
	{
		GridLayout disposition = new GridLayout(nbrElemX, nbrElemY);

		this.frame = new JFrame(frameName);
		this.frame.setSize(frameWidth, frameHeight);
		this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.frame.setLocation(450, 200);
		this.frame.setVisible(true);
		this.frame.setLayout(disposition);

		this.player1Name	= new JTextField();
		this.player2Name	= new JTextField();

		this.dimList		= new Integer[dimMax - dimMin+1];
		for (int i=dimMin; i<=dimMax; i++) dimList[i-dimMin] = i;
		this.dimXCombo		= new JComboBox<Integer>(dimList);
		this.dimYCombo		= new JComboBox<Integer>(dimList);
		this.dimXCombo.setSelectedIndex(5);
		this.dimYCombo.setSelectedIndex(4);

		ButtonGroup bg		= new ButtonGroup();
		this.twoIA			= new JRadioButton(twoIAText);
		this.IAPlayer2		= new JRadioButton(IAPlayer2Text);
		this.twoPlayers		= new JRadioButton(twoPlayersText);
		bg.add(twoIA); bg.add(IAPlayer2); bg.add(twoPlayers);
		this.twoPlayers.setSelected(true);

		this.IALevelCombo	= new JComboBox<String>(IALevelName);

		this.ok				= new JButton("Ok");
		this.cancel			= new JButton("Cancel");
		this.ok				.addActionListener(new ButtonListener("ok"));
		this.cancel			.addActionListener(new ButtonListener("cancel"));

		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(new JLabel(player1Text));	this.frame.add(player1Name);
		this.frame.add(new JLabel(player2Text));	this.frame.add(player2Name);
		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(new JLabel(dimXText));		this.frame.add(dimXCombo);
		this.frame.add(new JLabel(dimYText));		this.frame.add(dimYCombo);
		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(new JLabel(IALevelText));	this.frame.add(IALevelCombo);
		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(new JLabel(playersTypeText));this.frame.add(twoIA);
		this.frame.add(new JLabel());				this.frame.add(IAPlayer2);
		this.frame.add(new JLabel());				this.frame.add(twoPlayers);
		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(new JLabel());				this.frame.add(new JLabel());
		this.frame.add(ok);							this.frame.add(cancel);
	}

// ---------------------------------
// Button Listener
// ---------------------------------
	private class ButtonListener implements ActionListener
	{
		private String txt;
		public ButtonListener(String txt){this.txt = txt;}
		public void actionPerformed(ActionEvent arg0)
		{
			if (txt.equals("ok"))	ok();
			frame.setVisible(false);
			frame = null;
		}
		private void ok()
		{
			String p1Name	= player1Name.getText();
			String p2Name	= player2Name.getText();
			int w			= dimXCombo.getSelectedIndex()+dimMin;
			int h			= dimYCombo.getSelectedIndex()+dimMin;
			int IA			= IALevelCombo.getSelectedIndex();
			int players;
			if		(twoIA.isSelected())		players = 0;
			else if	(IAPlayer2.isSelected())	players = 1;
			else								players = 2;

			engine.newGame(w, h, players);
			gui.setPlayersName(p1Name, p2Name);
			gui.setGroundDim(w, h);
			
			if		(twoIA.isSelected())		{addIALevel(IA); addIALevel(IA); engine.startAIMatch();}
			else
			{
				if	(IAPlayer2.isSelected())	addIALevel(IA);
				gui.update();
			}
		}
		private void addIALevel(int IA)
		{
			switch(IA)
			{
				case 0: engine.addEasyAI();		break;
				case 1: engine.addMediumAI();	break;
				case 2: engine.addHardAI();		break;
				default: throw new RuntimeException("Unknown AI level: " + IA);
			}
		}
	}
}