package sebedo.window;

import sebedo.image.ImageLoader;
import sebedo.shape.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Loads all graphics and graphics-related objects, such as listeners.<br>
 * TODO: make vector graphics editor
 * <br>
 * TODO: add anti-aliasing
 */
public final class PaintPanel extends JPanel implements Actions, ImageLoader, ActionListener {
    private static PaintPanel paintPanel;
    private static BufferedImage bImage;
    private static Graphics2D g2d;

    /**
     * Menu bar bound to the {@code PaintPanel}.
     */
    public static final JMenuBar menuBar = new JMenuBar();

    private static final JMenu fileMenu = new JMenu("File");
    private static final JMenuItem saveMI = new JMenuItem("Save");
    private static final JMenuItem saveAsMI = new JMenuItem("Save as");
    private static final JMenuItem newFileMI = new JMenuItem("New File");

    private static final JMenuItem[] fileMenuItems = {
            saveMI,
            saveAsMI,
            newFileMI
    };

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
        }

        for (JMenuItem m : editMenuItems) {
            editMenu.add(m);
        }

        for (JMenuItem m : setToolMenuItems) {
            setToolMI.add(m);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    private static Tools selectedTool = Tools.FREEHAND;
    private static int toolIndex = 0;

    private static Point mouse0 = get().getMousePosition();
    private static Point mouse1 = get().getMousePosition();

    private static Panel shapePanel = new Panel(); // TODO: use this to fix refresh issue

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

    public static Color color;
    public static Color fillColor;
    public static Color bgColor;
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

        // bind menuItems to their respective actions
        saveMI.addActionListener(this);
        saveAsMI.addActionListener(this);
        newFileMI.addActionListener(this);

        copyMI.addActionListener(this);
        pasteMI.addActionListener(this);
        undoMI.addActionListener(this);
        redoMI.addActionListener(this);
        clearMI.addActionListener(this);
        freeHandToolMI.addActionListener(this);
        ellipseToolMI.addActionListener(this);
        rectangleToolMI.addActionListener(this);
        lineToolMI.addActionListener(this);
        arcToolMI.addActionListener(this);
        shapeToolMI.addActionListener(this);
        selectToolMI.addActionListener(this);

        // set default colors
        color = Color.BLACK;
        fillColor = new Color(255, 255, 255, 0);
        bgColor = new Color(255, 255, 255, 0);
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
        update();
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
                freeHandPath.append(l, true);
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
                ellipse.setFrameFromDiagonal(mouse0, mouse1);
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
                rectangle.setFrameFromDiagonal(mouse0, mouse1);
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
                line.setLine(mouse0, mouse1);
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
                System.out.print("Image written: ");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not write file.");
            }

            System.out.print(file.getName() + "\n");
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
            default: repaint(); break;
        }

        // update various components to match accepted state
        if (ToolPanel.toolSelector.getSelectedItem() != PaintPanel.selectedTool.toString()) {
            ToolPanel.toolSelector.setSelectedItem(PaintPanel.selectedTool.toString());
        }
    }

    public void rasterDraw(Graphics g) {
        g2d = (Graphics2D) g;

        // refresh the screen (to remove smearing effect)
        g2d.setColor(Color.WHITE);
        g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));

        // redraw the drawStack
        for (Object o : DrawStack.get()) {
            SebedoGraphic s;
            BufferedImage i;
            if (o instanceof SebedoGraphic) {
                s = (SebedoGraphic) o;
                i = null;
            } else {
                s = null;
                i = (BufferedImage) o;
            }

            if (!(o instanceof BufferedImage)) {
                if (s != null) {
                    g2d.setColor(s.getColor());
                    g2d.setStroke(s.getStroke());
                    g2d.draw((java.awt.Shape) o);
                }
            } else {
                g2d.drawImage(i, null, 0, 0);
            }

            if (!(o instanceof SebedoPath || o instanceof SebedoLine || o instanceof BufferedImage)) {
                g2d.setColor(s.getFill());
                g2d.fill((java.awt.Shape) o);
            }
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
        rasterDraw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMI) {
            export();
        } else if (e.getSource() == saveAsMI) {
            // TODO: upgrade export
        } else if (e.getSource() == newFileMI) {
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
                color = Color.BLACK;
                bgColor = Color.WHITE;
                fillColor = new Color(0, 0, 0, 0);
            }
        } else if (e.getSource() == copyMI) {
            // need to make copy
        } else if (e.getSource() == pasteMI) {
            // need to make paste
        } else if (e.getSource() == undoMI) {
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
}
