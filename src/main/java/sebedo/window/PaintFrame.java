package sebedo.window;

import javax.swing.*;

import java.awt.*;

import static sebedo.image.ImageLoader.loadImage;

/**
 * Loads the PaintPanel on the JFrame.
 * @see PaintPanel
 */
public class PaintFrame extends JFrame {
    public static int width, height;
    private static final String title = "Sebedo Paint";

    private static PaintFrame paintFrame;

    private PaintFrame() {
        width = 800;
        height = 600;
        setJMenuBar(PaintPanel.menuBar);

        init();
        setVisible(true);

        System.out.println("Welcome to Sebedo Graphics Engine.");
    }

    public static PaintFrame get() {
        if (paintFrame == null) {
            paintFrame = new PaintFrame();
        }

        return paintFrame;
    }

    public void init() {
        add(PaintPanel.get());
        pack();

        setTitle(title);
        setSize(width, height);
        setIconImage(loadImage("src/main/resources/testImages/cookie.png"));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PaintPanel.get().requestFocusInWindow();
    }
}
