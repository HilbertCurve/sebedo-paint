import sebedo.image.ImageLoader;
import sebedo.window.Frame;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Loads the frame singleton made by the Frame class.
 * @see Frame
 */
public class Main implements ImageLoader {
    // the frame is the actual window thing, and is located in Frame

    public static Frame frame;

    public static void main(String[] args) {
        try {
            EventQueue.invokeAndWait(() -> frame = Frame.get());
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> frame.run());
    }
}
