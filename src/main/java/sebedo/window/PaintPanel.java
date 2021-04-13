package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.Stack;

/**
 * Loads all graphics and graphics-related objects, such as listeners.
 */
public class PaintPanel extends JPanel {
    private static PaintPanel pPanel;

    private final Stack<Object> drawStack = new Stack<>();

    private static boolean isPainting;

    private static Point p0 = new Point(-1, -1);
    private static Point p1 = new Point(-1, -1);
    private static Line2D l = new Line2D.Float(p0, p1);
    private static int count;
    private static Color color;
    private static QuadCurve2D curve;

    /**
     * Private constructor for class {@code PaintPanel}
     * @see PaintPanel#get
     */
    private PaintPanel() {
        // add mouse and key listeners
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
                p0 = p1;
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    color = Color.BLUE;
                } else {
                    color = Color.BLACK;
                }
                update();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("e");
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // get mouse and keyboard to focus on this panel
        this.requestFocusInWindow();
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
        g2d.setColor(color);
        g2d.draw(l);

    }
}
