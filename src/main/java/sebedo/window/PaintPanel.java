package sebedo.window;

import sebedo.shape.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Loads all graphics and graphics-related objects, such as listeners.
 * <br>
 * TODO: add bit-art editor
 * <br>
 * TODO: class is too big, must disperse
 */
public final class PaintPanel extends JPanel implements Actions, ActionListener {
    /* Important static fields */
    private static PaintPanel paintPanel;
    private static BufferedImage bImage;
    private static Graphics2D g2d;
    private static final RenderingHints r = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
    );

    /**
     * Menu bar bound to {@code PaintPanel}.
     */
    public static final JMenuBar menuBar = new JMenuBar();

    /**
     * Menu of file-related menu items.
     */
    private static final JMenu fileMenu = new JMenu("File");
    private static final JMenuItem saveMI = new JMenuItem("Save");
    private static final JMenuItem saveAsMI = new JMenuItem("Save as");
    private static final JMenuItem newFileMI = new JMenuItem("New File");

    /**
     * Menu of editing-related menu items.
     */
    private static final JMenu editMenu = new JMenu("Edit");
    private static final JMenuItem copyMI = new JMenuItem("Copy");
    private static final JMenuItem pasteMI = new JMenuItem("Paste");
    private static final JMenuItem undoMI = new JMenuItem("Undo");
    private static final JMenuItem redoMI = new JMenuItem("Redo");
    private static final JMenuItem clearMI = new JMenuItem("Clear");
    private static final JMenu setToolMI = new JMenu("Set Tool");
    private static final JMenuItem freeHandToolMI = new JMenuItem("Freehand");
    private static final JMenuItem ellipseToolMI = new JMenuItem("Ellipse");
    private static final JMenuItem rectangleToolMI = new JMenuItem("Rectangle");
    private static final JMenuItem lineToolMI = new JMenuItem("Line");
    private static final JMenuItem arcToolMI = new JMenuItem("Arc");
    private static final JMenuItem shapeToolMI = new JMenuItem("Shape");
    private static final JMenuItem selectToolMI = new JMenuItem("Select");

    private static final JMenuItem[] fileMenuItems = {
            saveMI,
            saveAsMI,
            newFileMI
    };

    private static final JMenuItem[] editMenuItems = {
            copyMI,
            pasteMI,
            undoMI,
            redoMI,
            clearMI,
            setToolMI
    };

    private static final JMenuItem[] setToolMenuItems = {
            freeHandToolMI,
            ellipseToolMI,
            rectangleToolMI,
            lineToolMI,
            arcToolMI,
            shapeToolMI,
            selectToolMI
    };

    static {
        for (JMenuItem m : fileMenuItems) {
            fileMenu.add(m);
            m.addActionListener(PaintPanel.get());
        }

        for (JMenuItem m : editMenuItems) {
            editMenu.add(m);
            m.addActionListener(PaintPanel.get());
        }

        for (JMenuItem m : setToolMenuItems) {
            setToolMI.add(m);
            m.addActionListener(PaintPanel.get());
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }


    /**
     * Current selected tool.
     * @see PaintPanel#setSelectedTool
     * @see PaintPanel#switchTool
     */
    private static Tools selectedTool = Tools.SELECT;
    private static int toolIndex = 0;

    /* Used for mouse-related shenanigans. */
    private static Point mouse0 = get().getMousePosition();
    private static Point mouse1 = get().getMousePosition();

    // private static Panel shapePanel = new Panel(); TODO: use this to fix refresh issue

    /**
     * Static {@code SebedoPath} used in {@code freeHandDraw}.
     * @see PaintPanel#freeHandDraw()
     */
    private static SebedoPath freeHandPath = new SebedoPath();

    /**
     * Static {@code SebedoEllipse} used in {@code ellipseDraw}.
     * @see PaintPanel#ellipseDraw()
     */
    private static SebedoEllipse ellipse = new SebedoEllipse();

    /**
     * Static {@code SebedoRectangle} used in {@code rectangleDraw}.
     * @see PaintPanel#rectangleDraw()
     */
    private static SebedoRectangle rectangle = new SebedoRectangle();

    /**
     * Static {@code Line2D.Double} used in {@code lineDraw}.
     * @see PaintPanel#lineDraw()
     */
    private static SebedoLine line = new SebedoLine();

    /**
     * Static {@code Arc2D.Double} used in {@code arcDraw}.
     * @see PaintPanel#arcDraw()
     */
    private static Arc2D arc = new Arc2D.Double();

    /**
     * Current border/main color.
     */
    public static Color color;
    /**
     * Current fill color.
     */
    public static Color fillColor;
    /**
     * Current background color.
     */
    public static Color bgColor;
    /**
     * Current stroke weight.
     */
    public static int strokeWeight = 1;

    public static boolean isPainting;

    public static boolean isRasterDraw = true;

    public static boolean isBitArtDraw;

    /**
     * Set of currently pressed keys.
     */
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

        // set booleans
        isBitArtDraw = true;

        // set default colors
        color = Color.WHITE;
        fillColor = new Color(0, 0, 0, 0);
        bgColor = Color.BLACK;
        this.setBackground(bgColor);
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

    /**
     * Cycles through {@code selectedTool}.
     */
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

    /**
     * Sets {@code selectedTool} to value from enum {@code Tools}.
     * @see Tools
     */
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
            freeHandPath = new SebedoPath();
        }

        if (isPainting) {
            if (mouseInBounds) {
                ((Path2D.Double) freeHandPath.getAwtInstance()).append(l, true);
            }
            if (!DrawStack.get().contains(freeHandPath)) {
                DrawStack.get().push(freeHandPath);
            }
        } else {
            freeHandPath = new SebedoPath();
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

        if (ellipse == null) {
            ellipse = new SebedoEllipse();
        }

        if (isPainting) {
            try {
                ((Ellipse2D.Double) ellipse.getAwtInstance()).setFrameFromDiagonal(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(ellipse)) {
                DrawStack.get().add(ellipse);
            }
        } else {
            ellipse = null;
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

        if (rectangle == null) {
            rectangle = new SebedoRectangle();
        }

        if (isPainting) {
            try {
                ((Rectangle2D.Double) rectangle.getAwtInstance()).setFrameFromDiagonal(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(rectangle)) {
                DrawStack.get().add(rectangle);
            }
        } else {
            rectangle = null;
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

        if (line == null) {
            line = new SebedoLine();
        }

        if (isPainting) {
            try {
                ((Line2D.Double) line.getAwtInstance()).setLine(mouse0, mouse1);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(line)) {
                DrawStack.get().add(line);
            }
        } else {
            line = null;
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

        if (arc == null) {
            arc = new Arc2D.Double();
        }

        if (isPainting) {
            try {
                arc.setArc(mouse0.getX(), mouse0.getY(), mouse1.getX(), mouse1.getY(), 180, 180, Arc2D.OPEN);
            } catch (NullPointerException ignored) {

            }

            if (!DrawStack.get().contains(arc)) {
                DrawStack.get().add(arc);
            }
        } else {
            arc = null;
            mouse0 = null;
        }

        repaint();
    }

    public void setIsBitArtDraw(boolean bool) {
        isBitArtDraw = bool;
        update();
    }

    private void open() {
        pressedKeys.clear(); // otherwise I get sticky keys

        Object[] options = {
                "Yes",
                "No"
        };

        int n = JOptionPane.showOptionDialog(
                this,
                "All unsaved progress will be lost.\n"
                + "Continue?",
                "Open File",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]
        );

        switch (n) {
            case JOptionPane.YES_OPTION:
                final JFileChooser chooser = new JFileChooser();
                int val = chooser.showOpenDialog(PaintPanel.this);
                System.out.println("Dialog finished.");

                if (val == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    System.out.println("Opening file: " + file.getName());

                    try {
                        DrawStack.get().clear();
                        bImage = ImageIO.read(file);
                        System.out.println("File opened: " + file.getName());
                        DrawStack.get().push(bImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("An error occurred.");
                    }

                } break;
            case JOptionPane.NO_OPTION:
                System.out.println("Open Cancelled."); break;
        }

        repaint();
    }

    private void export() {
        final JFileChooser chooser = new JFileChooser();
        int val;

        // otherwise I get sticky keys
        pressedKeys.clear();
        val = chooser.showSaveDialog(PaintPanel.this);
        System.out.println("Dialog finished.");

        if (val == JFileChooser.APPROVE_OPTION) {
            Graphics g = bImage.getGraphics();
            this.printAll(g);

            File file = chooser.getSelectedFile();
            System.out.println("PaintPanel loaded.");

            try {
                ImageIO.write(bImage, "png", file);
                System.out.print("Image written: " + file.getName() + ".\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not write file.");
            }
        }
    }

    /**
     * Updates {@code drawStack} whenever a listener is called, or when specified elsewhere.
     */
    public synchronized void update() {
        switch (selectedTool) {
            case FREEHAND: freeHandDraw(); break;
            case ELLIPSE: ellipseDraw(); break;
            case RECTANGLE: rectangleDraw(); break;
            case LINE: lineDraw(); break;
            case ARC: arcDraw(); break;
            default: repaint(); break;
        }

        // update various components to match accepted state
        if (ToolPanel.get().toolSelector.getSelectedItem() != PaintPanel.selectedTool.toString()) {
            ToolPanel.get().toolSelector.setSelectedItem(PaintPanel.selectedTool.toString());
        }
        if (PaintFrame.get().getBackground() != bgColor) {
            PaintFrame.get().setBackground(bgColor);
        }
    }

    public void rasterDraw(Graphics g) {
        g2d = (Graphics2D) g;


        // refresh the screen (to remove smearing effect)
        if (isBitArtDraw) {
            if (g2d.getRenderingHints() == r) {
                g2d.setRenderingHints(null);
            }
            for (int i = 0; i < Math.ceil(16/Grid.gridSize); i++) {
                for (int j = 0; j < Math.ceil(9/Grid.gridSize); j++) {
                    g2d.drawImage(
                        Grid.getScaledGrid(),
                        new AffineTransform(
                            1, 0,
                            0, 1,
                            i * Grid.getScaledGrid().getWidth(null), j * Grid.getScaledGrid().getHeight(null)),
                            null);
                }
            }
        } else {
            // enable antialiasing
            if (g2d.getRenderingHints() != r) {
                g2d.setRenderingHints(r);
            }
            g2d.setColor(bgColor);
            g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
        }

        // redraw the drawStack
        for (Object o : DrawStack.get()) {
            SebedoShape s = null;
            BufferedImage i = null;

            if (o instanceof SebedoShape) {
                s = (SebedoShape) o;
            } else {
                i = (BufferedImage) o;
            }

            if (!(o instanceof BufferedImage)) {
                if (!(o instanceof SebedoPath || o instanceof SebedoLine)) {
                    g2d.setColor(s.getFill());
                    g2d.fill(((SebedoShape) o).getAwtInstance());
                }

                g2d.setColor(s.getColor());
                g2d.setStroke(s.getStroke());
                g2d.draw(((SebedoShape) o).getAwtInstance());
            } else {
                g2d.drawImage(i, null, 0, 0);
            }
        }
    }

    // TODO: finish vectorDraw
    public void vectorDraw(Graphics g) {
        g2d = (Graphics2D) g;
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
            case (int) SAVE: export(); break;
            case (int) OPEN: open(); break;
        }
    }

    /**
     * Overloaded paint method from JComponent, paints all components in the DrawStack singleton.
     * @see JComponent#paint
     * @see DrawStack
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isRasterDraw) {
            rasterDraw(g);
        } else {
            vectorDraw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* If menu item do menu item things */
        if (e.getSource() instanceof JMenuItem) {
            if (e.getSource() == saveMI) {
                export();
            } else /*if (e.getSource() == saveAsMI) {
                TODO: upgrade export
            } else*/ if (e.getSource() == newFileMI) {
                Object[] options = {
                        "Yes",
                        "No"
                };

                int n = JOptionPane.showOptionDialog(
                        this,
                        "All unsaved progress will be lost.\n"
                                + "Continue?",
                        "New File",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]
                );

                if (n == JOptionPane.YES_OPTION) {
                    DrawStack.get().clear();
                    color = Color.WHITE;
                    bgColor = Color.BLACK;
                    fillColor = new Color(0, 0, 0, 0);
                }
            } else /*if (e.getSource() == copyMI) {
                // copy
            } else if (e.getSource() == pasteMI) {
                // paste
            } else*/ if (e.getSource() == undoMI) {
                DrawStack.get().undo();
            } else if (e.getSource() == redoMI) {
                DrawStack.get().redo();
            } else if (e.getSource() == clearMI) {
                DrawStack.get().clear();
            } else if (e.getSource() == freeHandToolMI) {
                setSelectedTool(Tools.FREEHAND);
            } else if (e.getSource() == ellipseToolMI) {
                setSelectedTool(Tools.ELLIPSE);
            } else if (e.getSource() == rectangleToolMI) {
                setSelectedTool(Tools.RECTANGLE);
            } else if (e.getSource() == lineToolMI) {
                setSelectedTool(Tools.LINE);
            } else if (e.getSource() == arcToolMI) {
                setSelectedTool(Tools.ARC);
            } else if (e.getSource() == shapeToolMI) {
                setSelectedTool(Tools.SHAPE);
            } else if (e.getSource() == selectToolMI) {
                setSelectedTool(Tools.SELECT);
            }
        }
        /* The following will be for other performed actions... */
    }
}
