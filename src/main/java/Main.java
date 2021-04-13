import sebedo.image.ImageLoader;
import sebedo.window.Frame;

/**
 * Loads the frame singleton made by the Frame class.
 * @see Frame
 */
public class Main implements ImageLoader {
    // the frame is the actual window thing, and is located in Frame

    public static Frame frame;

    public static void main(String[] args) {
        frame = Frame.get();
        frame.run();
    }
}
