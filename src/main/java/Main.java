import sebedo.image.ImageLoader;
import sebedo.window.PaintFrame;
import sebedo.window.PaintPanel;
import sebedo.window.ToolFrame;

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
        toolFrame.setLocation(paintFrame.getX() + paintFrame.getWidth() + 10, paintFrame.getY());

        main.run();
    }

    /**
     * Updates the PaintPanel repeatedly.
     */
    @Override
    public void run() {
        while (PaintPanel.get().isEnabled()) {
            // this is for things like frame rate; mainly a debugging tool
            // double dt1 = System.nanoTime();

            PaintPanel.get().update();

            /*
             * this halts processing for 15 milliseconds, allowing the CPU to do CPU stuff
             * for that amount of time (otherwise, this program would be a memory hog).
             */
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // double dt2 = System.nanoTime();
            // System.out.println(1 / ((dt2 - dt1) * 1E-9));
        }
    }
}
