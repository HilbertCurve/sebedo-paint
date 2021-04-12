package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

/**
 * Loads all graphics and graphics-related objects, such as listeners.
 */
public class PaintPanel extends JPanel {
    private static PaintPanel pPanel;

    private static boolean isPainting;

    private static Point p0 = new Point(-1, -1);
    private static Point p1 = new Point(-1, -1);
    private static Line2D l = new Line2D.Float(p0, p1);

    /**
     * Private constructor for class {@code PaintPanel}
     * @see PaintPanel#get
     */
    private PaintPanel() {
        // get mouse and keyboard to focus on this panel
        this.requestFocusInWindow();

        // add mouse listeners
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPainting = true;
                p0 = e.getPoint();
                p1 = e.getPoint();
                update();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPainting = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                p1 = e.getPoint();
                update();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    /**
     * Gets singleton PaintPanel
     * @return Singleton PaintPanel {@code pPanel}
     */
    public static PaintPanel get() {
        if (PaintPanel.pPanel == null) {
            PaintPanel.pPanel = new PaintPanel();
        }

        return PaintPanel.pPanel;
    }
    /**
     * Updates line whenever listener is called, or when specified elsewhere (soon to update other graphics as well).
     */
    public void update() {
        l = new Line2D.Float(p0, p1);
        repaint();
    }

    /**
     * Overloaded paint method from JComponent, paints specified components while {@code isPainting == true}.
     * @see JComponent#paint
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isPainting) {
            // refresh background
            g2d.setColor(Color.WHITE);
            g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
            // draw line
            g2d.setColor(Color.BLACK);
            g2d.draw(l);
        }
    }
}
