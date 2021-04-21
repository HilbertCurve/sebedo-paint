import sebedo.image.ImageLoader;
import sebedo.window.Frame;

/**
 * Loads the frame singleton made by the Frame class.
 * @see Frame
 * @see Frame#run
 */
public class Main implements ImageLoader {
    public static Frame frame;

    public static void main(String[] args) {
        frame = Frame.get();
        frame.run();
    }
}
