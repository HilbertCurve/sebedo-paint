package sebedo.window;

import sebedo.entity.sprite.SpritePanel;
import sebedo.window.image.ImageLoader;

import java.awt.*;

/**
 * Loads frames made by the FrameInit class.
 *
 * Currently made to load a main window and a debug window (I have yet to find a use for it, but I definitely think I
 * will.)
 */
public class FrameInitLoader implements ImageLoader {
    // the frame is the actual window thing, and is located in FrameInit

    SpritePanel spritePanel = new SpritePanel();
    DebugPanel debugPanel = new DebugPanel();

    FrameInit f = new FrameInit();
    FrameInit debug = new FrameInit();

    protected FrameInitLoader() {
        f.makeFrame(spritePanel, "Main");
        f.setIconImage(loadImage("src/resources/images/cookie.png"));

        debug.makeFrame(debugPanel, "Debug");
        debug.setIconImage(loadImage("src/resources/images/computerE.png"));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameInitLoader main = new FrameInitLoader();
            main.f.setVisible(true);
            main.debug.setVisible(true);

            Point p = main.f.getLocationOnScreen();
            p.setLocation(p.x + main.f.getLocation().x, p.y);

            main.debug.setLocation(p);
        });
    }
}
