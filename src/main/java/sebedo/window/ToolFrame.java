package sebedo.window;

import javax.swing.*;
import java.awt.*;

public class ToolFrame extends JFrame implements Runnable {
    private final int width, height;
    private final String title;

    private static ToolFrame toolFrame;

    private ToolFrame() {
        this.width = 200;
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
        add(ToolPanel.get());
        pack();

        setTitle(title);
        setSize(this.width, this.height);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Updates the ToolPanel repeatedly. Doesn't do much right now.
     */
    @Override
    public void run() {
        // does nothing right now.
    }
}
