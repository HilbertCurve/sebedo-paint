import sebedo.image.ImageLoader;
import sebedo.window.PaintFrame;
import sebedo.window.PaintPanel;
import sebedo.window.ToolFrame;

import javax.swing.*;

/**
 * Loads the frame singleton made by the Frame class.
 * @see PaintFrame
 * @see Main#run
 */
public class Main implements ImageLoader, Runnable {
    public static PaintFrame paintFrame;
    public static ToolFrame toolFrame;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static Main main = new Main();

    static {
        try {
            UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
        }
        catch (Throwable anything) {
            anything.printStackTrace();
        }
    }

    public static void main(String[] args) {

        toolFrame = ToolFrame.get();
        paintFrame = PaintFrame.get();
        toolFrame.setLocation(paintFrame.getX() + paintFrame.getWidth() + 10, paintFrame.getY());

        main.run();
    }

    /**
     * Updates PaintPanel repeatedly.
     */
    @Override
    public void run() {
        while (PaintPanel.get().isEnabled()) {
            // this is for things like frame rate; mainly a debugging tool
            // double dt1 = System.nanoTime();

            // PaintPanel.get().update();

            /*
             * this halts processing for 5 milliseconds, allowing the CPU to do CPU stuff
             * for that amount of time (otherwise this program would use more memory than
             * necessary and could cause runtime issues).
             */
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // double dt2 = System.nanoTime();
            // System.out.println(1 / ((dt2 - dt1) * 1E-9));
        }
    }
}
