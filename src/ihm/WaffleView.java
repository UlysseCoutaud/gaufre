package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import engine.Engine;






@SuppressWarnings("serial")
public class WaffleView extends JPanel implements MouseListener
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private final String	poisonImagePath			= "images/poison.png";
	private final float		paddingWidth			= (float) (0./10.);
	private final float		paddingHeight			= (float) (0./10.);
	private final float		waffleShadeProportion	= (float) (1./10.);
	private final int		nbrWaffleShades			= 15;

	private Color			backgroundColor			= Color.BLACK;
	private Color			waffleColor				= Color.orange;
	private Color			borderColor				= Color.DARK_GRAY;

	private BufferedImage 	image;
	private BufferedImage	poisonImage;
	private Engine			engine;
	private int				cellWidth;				// Cell size (px)
	private int				cellHeight;
	private int				xMin;					// Most left and high waffle point (px)
	private int				yMin;

// --------------------------------------------
// Constructeur:
// --------------------------------------------
	public WaffleView(int width, int height, Engine engine) throws IOException
	{
		this.engine 	= engine;
		this.poisonImage= ImageIO.read(new File(poisonImagePath));

		this.addMouseListener(this);
		this.setSize(width, height);
		// The second part of the init is donne by this.resize
	}

// --------------------------------------------
// Mouse Listener:
// --------------------------------------------
	public void mouseClicked(MouseEvent e)
	{
throw new RuntimeException("A faireeeeeeeeeeeee########################");
	}
	public void mousePressed(MouseEvent e)	{mouseClicked(e);}
    public void mouseEntered(MouseEvent e)	{}
    public void mouseExited(MouseEvent e)	{}
    public void mouseReleased(MouseEvent e)	{}

// --------------------------------------------
// Window:
// --------------------------------------------
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(this.image, UNDEFINED_CONDITION, UNDEFINED_CONDITION, null);
	}
	public int getWidth()	{return this.image.getWidth();}
	public int getHeight()	{return this.image.getHeight();}

	
	
	
	
	
	
	
	public void update()
	{
//		CurrentState cs		= engine.getCurrentState();
class CurrentState {
public boolean isWaffel(int x, int y) {return true;}
public boolean isEaten(int x, int y) {return false;}
public boolean isPoison(int x, int y) {return false;}
}
CurrentState cs = new CurrentState();
this.cellWidth	= (getWidth() - 2*xMin) / 10;		// Waffle cell size
this.cellHeight	= (getHeight() - 2*yMin) / 10;
		Graphics2D drawable = this.image.createGraphics();
		int xp, yp;

		for (int x=0; x<10; x++)//////////engine.getWidth(); x++)							// For each wafle cell: draw the cell
		{
			xp = xMin + x*cellWidth;
			for (int y=0; y<10; y++)////////engine.getHeight(); y++)
			{
				yp = yMin + y*cellHeight;
				if		(cs.isWaffel(x, y))	drawWaffle		(xp, yp, drawable);
				else if	(cs.isEaten(x, y))	drawEatenWaffle	(xp, yp, drawable);
				else if	(cs.isPoison(x, y))	drawPoison		(xp, yp, drawable);
				else throw new RuntimeException("Unknown case");
			}
		}
		this.repaint();
	}
	public void resize(int width, int height)
	{
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.xMin		= (int) (getWidth()*paddingWidth);						// Position of the most left waffle point
		this.yMin		= (int) (getHeight()*paddingHeight);
/////		this.cellWidth	= (getWidth() - 2*xMin) / engine.getWidth();		// Waffle cell size
/////		this.cellHeight	= (getHeight() - 2*yMin) / engine.getHeight();
		Graphics2D drawable = this.image.createGraphics();
		drawable.setColor(this.backgroundColor);
		drawable.fillRect(0, 0, width, height);
		this.update();
	}

// --------------------------------------------
// Auxiliary methods:
// --------------------------------------------
	/**============================================================
	 * Draww a waffle cell described by the most left and high pixel (xp, yp)
	 ==============================================================*/
	private void drawWaffle(int xp, int yp, Graphics2D drawable)
	{
		Point p0	= new Point(xp, yp);
		float w	= cellWidth;
		float h	= cellHeight;
		float dx	= (float)cellWidth * waffleShadeProportion/nbrWaffleShades;
		float dy	= (float)cellHeight* waffleShadeProportion/nbrWaffleShades;

		Color c = new Color(0, 0, 0);
		int dr	= borderColor.getRed()	/ nbrWaffleShades;
		int dg	= borderColor.getGreen()/ nbrWaffleShades;
		int db	= borderColor.getBlue()	/ nbrWaffleShades;
		for (int shade=0; shade<nbrWaffleShades; shade++)									// Draw the border shades
		{
			drawable.setPaint(c);
			drawable.fillRect(p0.x, p0.y, (int)w, (int)h);
			p0.x	+= dx;
			p0.y	+= dy;
			w		-= 2*dx;
			h		-= 2*dy;
			c = new Color(c.getRed()+dr, c.getGreen()+dg, c.getBlue()+db);
		}
		drawable.setColor(this.waffleColor);												// Draw the central color
		drawable.fillRect(p0.x+(int)dx, p0.y+(int)dy, (int)w, (int)h);
	}
	/**============================================================
	 * Draww an empty waffle cell described by the most left and high pixel (xp, yp)
	 ==============================================================*/
	private void drawEatenWaffle(int xp, int yp, Graphics2D drawable)
	{
		drawable.setColor(this.backgroundColor);
		drawable.fillRect(xp, yp, cellWidth, cellHeight);
	}
	/**============================================================
	 * Draww a poisoned waffle cell described by the most left and high pixel (xp, yp)
	 ==============================================================*/
	private void drawPoison(int xp, int yp, Graphics2D drawable)
	{
		drawEatenWaffle(xp, yp, drawable);
		drawable.drawImage(poisonImage, xp, yp, cellWidth, cellHeight, null);
	}
}