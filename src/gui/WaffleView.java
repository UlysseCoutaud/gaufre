package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import engine.Engine;
import engine.GameState;

@SuppressWarnings("serial")
public class WaffleView extends JPanel implements MouseListener, MouseMotionListener
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private final String poisonImagePath		= "images/poison.png";
	private final float paddingWidth			= (float) (1. / 10.);
	private final float paddingHeight			= (float) (1. / 10.);
	private final float waffleShadeProportion	= (float) (1. / 10.);
	private final int nbrWaffleShades			= 5;
	private final int dashLength				= 5;

	private Color backgroundColor				= Color.WHITE;
	private Color waffleColor					= new Color(255, 204, 102);
	private Color borderOutColor				= new Color(51, 0, 0);
	private Color borderInColor					= new Color(255, 102, 0);

	private BufferedImage image;
	private BufferedImage poisonImage;
	private Engine engine;
	private int cellWidth;						// Cell size (px)
	private int cellHeight;
	private int xMin;							// leftmost and high waffle point (px)
	private int yMin;
	private Integer selectedX, selectedY;

// --------------------------------------------
// Constructeur:
// --------------------------------------------
	public WaffleView(int width, int height, Engine engine) throws IOException
	{
		this.engine = engine;
		this.poisonImage = ImageIO.read(new File(poisonImagePath));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setSize(width, height);
		// The second part of the init is donne by this.resize
	}

// --------------------------------------------
// Mouse Listener:
// --------------------------------------------
	public void mouseClicked(MouseEvent e)
	{
		int x = (e.getX() - xMin) / cellWidth;
		int y = (e.getY() - yMin) / cellHeight;

		engine.play(new Point(x, y));
	}
	public void mousePressed(MouseEvent e)	{}
    public void mouseEntered(MouseEvent e)	{}
    public void mouseExited(MouseEvent e)
    {
    	selectedX = selectedY = null;
    }
    public void mouseReleased(MouseEvent e)	{}
	public void mouseDragged(MouseEvent e)	{}
	public void mouseMoved(MouseEvent e)
	{
		selectedX = new Integer((e.getX() - xMin) / cellWidth);
		selectedY = new Integer((e.getY() - yMin) / cellHeight);
		update();
	}

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
		GameState gs		= engine.getCurrentGameState();
		Graphics2D drawable = this.image.createGraphics();
		int xp, yp, xmax, ymax;
		
		// Drawing the waffle
		for (int x=0; x<gs.width; x++)
		{
			xp = xMin + x*cellWidth;
			for (int y=0; y<gs.height; y++)
			{
				yp = yMin + y*cellHeight;
				if (gs.isSafeToEat(x, y))		drawWaffle(xp, yp, drawable);
				else if	(gs.isEaten(x, y))		drawEatenWaffle	(xp, yp, drawable);
				else if	(gs.isPoison(x, y))		drawPoison		(xp, yp, drawable);
				else throw new RuntimeException("Unknown case");
			}
		}
		
		// Tracing the selected zone
		if(selectedX != null) {
			drawable.setStroke(new BasicStroke(3));
			if(selectedX != 0 || selectedY != 0) {
				drawable.setPaint(backgroundColor);
				xmax = getWidth() - xMin;
				ymax = getHeight() - yMin;
				xp = xMin + selectedX*cellWidth;
				yp = yMin + selectedY*cellHeight;
				while(xp<xmax) {
					drawable.drawLine(xp, yp, xp+dashLength, yp);
					xp += dashLength + 8;
				}
				xp = xMin + selectedX*cellWidth;			
				while(yp<ymax) {
					drawable.drawLine(xp, yp, xp, yp+dashLength);
					yp += dashLength + 8;
				}
			}
			else
				drawable.setPaint(Color.RED);
			
			for (int x=selectedX; x<gs.width; x++) {
				xp = xMin + x*cellWidth;
				for (int y=selectedY; y<gs.height; y++)
				{
					yp = yMin + y*cellHeight;
					if(!gs.isEaten(x, y))stripeWaffle(xp, yp, drawable);
				}
			}
		}
		
		this.repaint();
	}
	public void resize(int width, int height)
	{
		GameState gs = this.engine.getCurrentGameState();

		this.image	= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.xMin	= (int) (getWidth()*paddingWidth);			// Position of the leftmost waffle point
		this.yMin	= (int) (getHeight()*paddingHeight);
		this.cellWidth	= (getWidth() - 2*xMin) / gs.width;		// Waffle cell size
		this.cellHeight	= (getHeight() - 2*yMin) / gs.height;
		Graphics2D drawable = this.image.createGraphics();
		drawable.setColor(this.backgroundColor);
		drawable.fillRect(0, 0, width, height);
		this.update();
	}

// --------------------------------------------
// Auxiliary methods:
// --------------------------------------------
	/**======================================================================
	 * Draws a waffle cell described by the leftmost and highest pixel (xp, yp)
	 ========================================================================*/
	private void drawWaffle(int xp, int yp, Graphics2D drawable)
	{
		Point p0 = new Point();
		Point p1 = new Point();

		drawShades(xp, yp, drawable, p0, p1);									// Draw the border shades
		drawable.setColor(this.waffleColor);									// Draw the central color
		drawable.fillRect(p0.x, p0.y, p1.x, p1.y);
	}
	/**======================================================================================
	 * Draws stripes on a waffle cell described by the leftmost and highest pixel (xp, yp)
	 ========================================================================================*/
	private void stripeWaffle(int xp, int yp, Graphics2D drawable)
	{
		Point p10, p11, p20, p21, p30, p31;
		p10 = new Point(xp,yp+cellHeight/2);
		p11 = new Point(xp+cellWidth/2,yp);
		p20 = new Point(xp,yp+cellHeight);
		p21 = new Point(xp+cellWidth,yp);
		p30 = new Point(xp+cellWidth/2,yp+cellHeight);
		p31 = new Point(xp+cellWidth,yp+cellHeight/2);

		drawable.drawLine(p10.x, p10.y, p11.x, p11.y);
		drawable.drawLine(p20.x, p20.y, p21.x, p21.y);
		drawable.drawLine(p30.x, p30.y, p31.x, p31.y);
	}
	/**================================================================================
	 * Draws an empty waffle cell described by the leftmost and highest pixel (xp, yp)
	 ==================================================================================*/
	private void drawEatenWaffle(int xp, int yp, Graphics2D drawable)
	{
		drawable.setColor(this.backgroundColor);
		drawable.fillRect(xp, yp, cellWidth, cellHeight);
	}
	/**================================================================================
	 * Draws a poisoned waffle cell described by the leftmost and highest pixel (xp, yp)
	 ==================================================================================*/
	private void drawPoison(int xp, int yp, Graphics2D drawable)
	{
		Point p0 = new Point();
		Point p1 = new Point();

		drawShades(xp, yp, drawable, p0, p1);									// Draw the border shades
		drawable.setColor(this.waffleColor);									// Draw the central color
		drawable.fillRect(p0.x, p0.y, p1.x, p1.y);
		drawable.drawImage(poisonImage, p0.x, p0.y, p1.x, p1.y, null);			// Draw the poison image
	}
	/**=======================================================================
	 * Draws the waffle shades in the cell described by
	 *  the leftmost and highest pixel (xp, yp)
	 *  The leftmost and highest point of the inside square is returned in p0
	 *  The width and height of the inside square is returned in p1
	 ==========================================================================*/
	private void drawShades(int xp, int yp, Graphics2D drawable, Point p0, Point p1)
	{
		float w		= cellWidth;
		float h		= cellHeight;
		float dx	= (float)cellWidth * waffleShadeProportion/nbrWaffleShades;
		float dy	= (float)cellHeight* waffleShadeProportion/nbrWaffleShades;

		Color c = new Color(borderOutColor.getRGB());
		int dr	= (borderInColor.getRed()	- borderOutColor.getRed())	/ nbrWaffleShades;
		int dg	= (borderInColor.getGreen()	- borderOutColor.getGreen())/ nbrWaffleShades;
		int db	= (borderInColor.getBlue()	- borderOutColor.getBlue())	/ nbrWaffleShades;

		p0.x = xp;
		p0.y = yp;
		for (int shade=0; shade<nbrWaffleShades; shade++)
		{
			drawable.setPaint(c);
			drawable.fillRect(p0.x, p0.y, (int) w, (int) h);
			p0.x += dx;
			p0.y += dy;
			w -= 2 * dx;
			h -= 2 * dy;
			c = new Color(c.getRed() + dr, c.getGreen() + dg, c.getBlue() + db);
		}
		p1.x = (int)w;
		p1.y = (int)h;
	}

}