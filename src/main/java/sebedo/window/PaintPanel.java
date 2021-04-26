package sebedo.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

/**
 * Loads all graphics and graphics-related objects, such as listeners.<br>
 * TODO: bind menu items to respective actions
 * <br>
 * TODO: make raster graphics editor
 * <br>
 * TODO: make file loading
 */
public class PaintPanel extends JPanel implements Actions {
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
            new MenuItem("Select Tool...")
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

    private static Tools selectedTool = Tools.FREEHAND;
    private static int toolIndex = 0;

    private static Point mouse0 = get().getMousePosition();
    private static Point mouse1 = get().getMousePosition();
    private static Path2D.Double gp = new Path2D.Double();
    private static Ellipse2D e = new Ellipse2D.Double();
    private static Rectangle2D r = new Rectangle2D.Double();
    private static Color color;
    private static Color bgColor;

    public static boolean isPainting;

    public static final Set<String> pressedKeys = new HashSet<>();

    /**
     * Private constructor for class {@code PaintPanel}
     * @see PaintPanel#get
     */
    private PaintPanel() {
        // parse keybindings
        KeyBindParser.parseKeyBinds();

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

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public synchronized void keyPressed(KeyEvent e) {
                pressedKeys.add(KeyEvent.getKeyText(e.getKeyCode()));

                if (!pressedKeys.isEmpty()) {
                    for (long l : actions) {
                        if (pressedKeys.containsAll(KeyBindParser.getKeyBind(l))) {
                            doAction(l);
                        }
                    }
                }
                System.out.println(pressedKeys);
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                pressedKeys.remove(KeyEvent.getKeyText(e.getKeyCode()));
            }

            @Override
            public void keyTyped(KeyEvent e) {

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

    private void switchTool() {
        Tools[] toolsArr = Tools.values();
        if (toolIndex + 1 < toolsArr.length) {
            toolIndex++;
        } else {
            toolIndex = 0;
        }
        selectedTool = toolsArr[toolIndex];
        System.out.println(selectedTool.toString());
    }

    private void freeHandDraw() {
        Line2D l;
        mouse0 = getMousePosition();
        mouse1 = getMousePosition();
        boolean mouseInBounds = (mouse0 != null && mouse1 != null);

        if (mouseInBounds) {
            l = new Line2D.Double(mouse0, mouse1);
        } else {
            l = new Line2D.Double();
        }

        if (gp == null) {
            gp = new Path2D.Double();
        }

        if (isPainting) {
            if (mouseInBounds) {
                gp.append(l, true);
            }
            if (!DrawStack.get().contains(gp)) {
                DrawStack.get().add(gp);
            }
        } else {
            gp = new Path2D.Double();
        }
    }

    private void ellipseDraw() {

        if (getMousePosition() != null) {
            if (mouse0 == null) {
                mouse0 = getMousePosition();
            }

            mouse1 = getMousePosition();
        }

        if (e == null) {
            e = new Ellipse2D.Double();
        }

        if (isPainting) {
            try {
                e.setFrameFromDiagonal(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(e)) {
                DrawStack.get().add(e);
            }
        } else {
            e = null;
            mouse0 = null;
        }
    }

    private void rectangleDraw() {
        if (mouse0 == null) {
            mouse0 = getMousePosition();
        }

        if (getMousePosition() != null) {
            mouse1 = getMousePosition();
        }

        if (r == null) {
            r = new Rectangle2D.Double();
        }

        if (isPainting) {
            try {
                r.setFrameFromDiagonal(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(r)) {
                DrawStack.get().add(r);
            }
        } else {
            r = null;
            mouse0 = null;
        }
    }

    /**
     * Updates {@code drawStack} whenever a listener is called, or when specified elsewhere.
     */
    public void update() {
        switch (selectedTool) {
            case FREEHAND: freeHandDraw(); break;
            case ELLIPSE: ellipseDraw(); break;
            case RECTANGLE: rectangleDraw(); break;
        }

        repaint();
    }

    @Override
    public void doAction(long action) {
        switch ((int) action) {
            case (int) COPY: break; //TODO: make copy
            case (int) PASTE: break; //TODO: make paste
            case (int) UNDO: DrawStack.get().undo(); break;
            case (int) REDO: DrawStack.get().redo(); break;
            case (int) CLEAR: DrawStack.get().clear(); break;
            case (int) SWITCH_TOOL: switchTool(); break;
        }
    }

    /**
     * Overloaded paint method from JComponent, paints specified components while {@code isPainting == true}.
     * @see JComponent#paint
     */
    @Override
    public void paint(Graphics g) {
        try {
            Graphics2D g2d = (Graphics2D) g;

            // redraw background (simulate refreshing the page)
            g2d.setColor(bgColor);
            g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));

            // redraw GeneralPath
            g2d.setColor(color);
            for (Object o : DrawStack.get()) {
                g2d.draw((java.awt.Shape) o);
            }
        } catch (Exception ignored) {

        }
    }
}
