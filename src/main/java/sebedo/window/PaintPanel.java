package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

public class PaintPanel extends JPanel {
    private static PaintPanel pPanel;

    private static boolean isPainting;

    private static Point p0 = new Point(0, 0);
    private static Point p1 = new Point(0, 0);

    private static Line2D l = new Line2D.Float(p0, p1);

    private PaintPanel() {
        this.requestFocusInWindow();
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPainting = true;
                p0 = e.getPoint();
                p1 = e.getPoint();
                update(e);
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
                isPainting = false;
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                p1 = e.getPoint();
                update(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public static PaintPanel get() {
        if (PaintPanel.pPanel == null) {
            PaintPanel.pPanel = new PaintPanel();
        }

        return PaintPanel.pPanel;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isPainting) {
            g2d.setColor(Color.WHITE);
            g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
            g2d.setColor(Color.BLACK);
        }
        g2d.draw(l);
    }

    public void update(MouseEvent e) {
        l = new Line2D.Float(p0, p1);
        repaint();
    }

}
