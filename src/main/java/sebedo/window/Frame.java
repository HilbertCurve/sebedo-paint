package sebedo.window;

import sebedo.image.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Loads the PaintPanel on the JFrame.
 * @see PaintPanel
 */
public class Frame extends JFrame implements ImageLoader, Runnable {
    private final int width, height;
    private final String title;

    private static Frame frame;

    private Frame() {
        this.width = 800;
        this.height = 400;
        this.title = "Sebedo Graphics Engine";
        this.init(PaintPanel.get());
        this.setVisible(true);
        System.out.println("Hello!");
    }

    public static Frame get() {
        if (frame == null) {
            frame = new Frame();
        }

        return frame;
    }

    public void init(JPanel jPanel) {
        add(jPanel);
        JScrollPane s = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(s);
        pack();

        setTitle(title);
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(loadImage("src/main/resources/images/cookie.png"));
    }

    public void run() {
        while (this.isEnabled()) {
            PaintPanel.get().update();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
