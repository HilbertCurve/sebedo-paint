package sebedo.window;

import javax.swing.*;
import java.awt.*;

public class ToolFrame extends JFrame implements Runnable {
    private final int width, height;
    private final String title;

    private static ToolFrame toolFrame;

    private ToolFrame() {
        this.width = 300;
        this.height = 600;
        this.title = "Sebedo Graphics Engine";

        this.init();
        this.setVisible(true);
    }

    public static ToolFrame get() {
        if (toolFrame == null) {
            toolFrame = new ToolFrame();
        }

        return toolFrame;
    }

    public void init() {
        setTitle(title);
        setSize(this.width, this.height);
        setResizable(true);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        add(ToolPanel.get());
        pack();
    }

    /**
     * Updates the ToolPanel repeatedly. Doesn't do much right now.
     */
    @Override
    public void run() {

    }
}
