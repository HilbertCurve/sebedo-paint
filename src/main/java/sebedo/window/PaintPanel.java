package sebedo.window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Loads all graphics and graphics-related objects, such as listeners.<br>
 * TODO: bind menu items to respective actions
 * <br>
 * FIXME: raster graphics editor
 * <br>
 * TODO: make vector graphics editor
 * <br>
 * TODO: make file loading
 */
public class PaintPanel extends JPanel implements Actions {
    private static PaintPanel paintPanel;
    private static BufferedImage bImage;
    private static Graphics2D g2d;

    /**
     * Menu bar bound to the {@code PaintPanel}.
     */
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

    /**
     * Static {@code Path2D.Double} used in {@code freeHandDraw}.
     * @see PaintPanel#freeHandDraw()
     */
    private static Path2D freeHandPath = new Path2D.Double();

    /**
     * Static {@code Ellipse2D.Double} used in {@code ellipseDraw}.
     * @see PaintPanel#ellipseDraw()
     */
    private static Ellipse2D e = new Ellipse2D.Double();

    /**
     * Static {@code Rectangle2D.Double} used in {@code rectangleDraw}.
     * @see PaintPanel#rectangleDraw()
     */
    private static Rectangle2D r = new Rectangle2D.Double();

    /**
     * Static {@code Line2D.Double} used in {@code lineDraw}.
     * @see PaintPanel#lineDraw()
     */
    private static Line2D l = new Line2D.Double();

    /**
     * Static {@code Arc2D.Double} used in {@code arcDraw}.
     * @see PaintPanel#arcDraw()
     */
    private static Arc2D a = new Arc2D.Double();

    private static Color color;
    private static Color bgColor;
    public static int strokeWeight = 1;

    public static boolean isPainting;

    public static final Set<String> pressedKeys = new HashSet<>();

    /**
     * Private constructor for class {@code PaintPanel}.<br>
     * Don't let anyone instantiate this class.
     * @see PaintPanel#get
     */
    private PaintPanel() {
        // parse keybindings
        KeyBindParser.parseKeyBinds();
        bImage = new BufferedImage(PaintFrame.width, PaintFrame.height, BufferedImage.TYPE_INT_ARGB);

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
     * Returns singleton PaintPanel
     * @return singleton PaintPanel {@code paintPanel}
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

    public void setSelectedTool(Tools t) {
        selectedTool = t;
        toolIndex = t.ordinal();
    }

    private void freeHandDraw() {
        Line2D.Double l;
        mouse0 = getMousePosition();
        mouse1 = getMousePosition();
        boolean mouseInBounds = (mouse0 != null && mouse1 != null);

        if (mouseInBounds) {
            l = new Line2D.Double(mouse0, mouse1);
        } else {
            l = new Line2D.Double();
        }

        if (freeHandPath == null) {
            freeHandPath = new Path2D.Double();
        }

        if (isPainting) {
            if (mouseInBounds) {
                freeHandPath.append(l, true);
            }
            if (!DrawStack.get().contains(freeHandPath)) {
                DrawStack.get().add(freeHandPath);
            }
        } else {
            freeHandPath = new Path2D.Double();
        }

        repaint();
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

        repaint();
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

        repaint();
    }

    private void lineDraw() {
        if (mouse0 == null) {
            mouse0 = getMousePosition();
        }

        if (getMousePosition() != null) {
            mouse1 = getMousePosition();
        }

        if (l == null) {
            l = new Line2D.Double();
        }

        if (isPainting) {
            try {
                l.setLine(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(l)) {
                DrawStack.get().add(l);
            }
        } else {
            l = null;
            mouse0 = null;
        }

        repaint();
    }

    // FIXME
    private void arcDraw() {
        if (mouse0 == null) {
            mouse0 = getMousePosition();
        }

        if (getMousePosition() != null) {
            mouse1 = getMousePosition();
        }

        if (a == null) {
            a = new Arc2D.Double();
        }

        if (isPainting) {
            try {
                a.setArc(mouse0.getX(), mouse0.getY(), mouse1.getX(), mouse1.getY(), 180, 180, Arc2D.OPEN);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(a)) {
                DrawStack.get().add(a);
            }
        } else {
            a = null;
            mouse0 = null;
        }

        repaint();
    }

    private void export(String dir, String type) {
        Graphics g = bImage.getGraphics();
        this.printAll(g);

        File file = new File(dir + "." + type);

        try {
            ImageIO.write(bImage, type, file);
        } catch (IOException e) {
            e.printStackTrace();
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
            case LINE: lineDraw(); break;
            case ARC: arcDraw(); break;
        }
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
            case (int) SAVE: export("src/main/resources/foo", "png");
        }
    }

    /**
     * Overloaded paint method from JComponent, paints all components in the DrawStack singleton.
     * @see JComponent#paint
     * @see DrawStack
     */
    @Override
    public void paint(Graphics g) {
        g2d = (Graphics2D) g;

        // redraw background (simulate refreshing the page)
        g2d.setColor(bgColor);
        g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));

        // redraw the drawStack
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWeight));
        for (Object o : DrawStack.get()) {
            g2d.draw((java.awt.Shape) o);
        }
    }
}
