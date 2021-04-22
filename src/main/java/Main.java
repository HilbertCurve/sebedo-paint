import sebedo.image.ImageLoader;
import sebedo.window.PaintFrame;
import sebedo.window.PaintPanel;
import sebedo.window.ToolFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Loads the frame singleton made by the Frame class.
 * @see PaintFrame
 * @see Main#run
 */
public class Main implements ImageLoader, Runnable {
    public static PaintFrame paintFrame;
    public static ToolFrame toolFrame;

    public static Main main = new Main();

    public static void main(String[] args) {
        paintFrame = PaintFrame.get();
        toolFrame = ToolFrame.get();
        toolFrame.setLocation(paintFrame.getX() + paintFrame.getWidth(), paintFrame.getY());

        main.run();
    }

    /**
     * Updates the PaintPanel repeatedly.
     */
    @Override
    public synchronized void run() {
        while (PaintPanel.get().isEnabled()) {
            PaintPanel.get().update();
        }
    }
}
