
package ihm;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import engine.Engine;

public class WaffleView extends JPanel implements MouseListener {

    // Properties

    private Engine engine;
    public float margin;

    // Constructors

    public WaffleView(Engine engine) {
        this.engine = engine;
    }

    // Mouse listener

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // Cells

    private Point cellPositionForMouseLocation(Point p) {
        return new Point(0, 0);
    }

    private Rectangle cellRectForPosition(Point p) {
        return Rectangle(0, 0, 0, 0);
    }

    // Drawings

    @Override
    protected void paintComponent(Graphics g) {

    }

}
