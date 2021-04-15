package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Stack;

/**
 * Loads all graphics and graphics-related objects, such as listeners.
 */
public class PaintPanel extends JPanel {
    private static PaintPanel paintPanel;

    public static final MenuBar menuBar = new MenuBar();

    private static final Menu fileMenu = new Menu("File");
    private static final MenuItem[] fileMenuItems = {
            new MenuItem("Save"),
            new MenuItem("Save as..."),
            new MenuItem("New File")
    };
    private static final Menu editMenu = new Menu("Edit");
    private static final MenuItem[] editMenuItems = {
            new MenuItem("Copy"),
            new MenuItem("Paste"),
            new MenuItem("Clear"),
            new MenuItem("Undo"),
            new MenuItem("Redo"),
            new MenuItem("")
    };


    static {
        for (MenuItem m : fileMenuItems) {
            fileMenu.add(m);
        }

        for (MenuItem m : editMenuItems) {
            editMenu.add(m);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    private static boolean isPainting;

    static class DrawStack extends Stack<Object> {
        private static DrawStack drawStack;

        private DrawStack() {
            new Stack<>();
        }

        public static DrawStack get() {
            if (drawStack == null) {
                drawStack = new DrawStack();
            }

            return drawStack;
        }

        public void undo() {
            isPainting = false;
            if (!DrawStack.get().empty()) {
                DrawStack.get().pop();
            }
            PaintPanel.get().update();
        }

        @Override
        public void clear() {
            super.clear();
            PaintPanel.isPainting = false;
            PaintPanel.get().update();
        }
    }

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
        // add menu bar


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
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    DrawStack.get().clear();
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    DrawStack.get().undo();
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
        if (PaintPanel.paintPanel == null) {
            PaintPanel.paintPanel = new PaintPanel();
        }

        return PaintPanel.paintPanel;
    }
    /**
     * Updates {@code drawStack} whenever a listener is called, or when specified elsewhere.
     */
    public void update() {
        if (mouse0 == null || mouse1 == null) {
            mouse0 = getMousePosition();
            mouse1 = getMousePosition();
        }

        Line2D l = new Line2D.Double(mouse0, mouse1);

        if (gp == null) {
            gp = new GeneralPath();
        }

        if (isPainting) {
            gp.append(l, true);
            if (!DrawStack.get().contains(gp)) {
                DrawStack.get().add(gp);
            }
        } else {
            gp = null;
        }

        repaint();
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
        for (Object o : DrawStack.get()) {
            g2d.draw((Shape) o);
        }
    }
}
