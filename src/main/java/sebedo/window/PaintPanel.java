package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Stack;

/**
 * Loads all graphics and graphics-related objects, such as listeners.<br>
 * TODO: make menu-bar
 */
public class PaintPanel extends JPanel {
    private static PaintPanel pPanel;

    private final Stack<Object> drawStack = new Stack<>();

    private static boolean isPainting;
    private static Point mouse0;
    private static Point mouse1;
    private static GeneralPath gp = new GeneralPath();
    private static Color color;
    private static Color bgColor;

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
                mouse0 = e.getPoint();
                mouse1 = e.getPoint();
                update();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPainting = false;
                update();
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
                mouse1 = e.getPoint();
                if (isPainting) {
                    update();
                }
                mouse0 = mouse1;
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
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    isPainting = false;
                    drawStack.clear();
                    update();
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    isPainting = false;
                    for (int i = 0; i < 10; i++) {
                        if (!drawStack.empty()) {
                            drawStack.pop();
                        } else {
                            break;
                        }
                    }
                    update();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // set default colors
        color = Color.BLACK;
        bgColor = Color.WHITE;

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
        Line2D l = new Line2D.Float(mouse0, mouse1);

        if (isPainting) {
            gp.append(l, true);
            drawStack.add(gp);
        }

        repaint();
        gp = new GeneralPath();
    }

    /**
     * Overloaded paint method from JComponent, paints specified components while {@code isPainting == true}.
     * @see JComponent#paint
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // redraw background (simulate refreshing the page)
        g2d.setColor(bgColor);
        g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));

        // redraw GeneralPath
        g2d.setColor(color);
        for (Object o : drawStack) {
            g2d.draw((Shape) o);
        }
    }
}