package sebedo.window;

import sebedo.image.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Loads the PaintPanel on the JFrame.
 * @see PaintPanel
 */
public class PaintFrame extends JFrame implements ImageLoader {
    private final int width, height;
    private final String title;

    private static PaintFrame paintFrame;

    // this is for things like frame rate; mainly a debugging tool
    private static double dt;

    private PaintFrame() {
        this.width = 800;
        this.height = 600;
        this.title = "Sebedo Graphics Engine";
        this.setMenuBar(PaintPanel.menuBar);

        this.init();
        this.setVisible(true);

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
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(loadImage("src/main/resources/images/cookie.png"));

        PaintPanel.get().requestFocusInWindow();
    }
}
