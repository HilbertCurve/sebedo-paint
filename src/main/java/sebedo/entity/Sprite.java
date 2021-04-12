package sebedo.entity;

import sebedo.image.ImageLoader;

import java.awt.*;
import java.io.File;

/**
 * The Sprite class extends the Entity class and is the primary object for visible entities with an interactive hit box.
 * These sprites could range from a playable character to an NPC to an enemy. What I want from a Sprite:<br>
 *  - Hit Box<br>
 *  - Image Data<br>
 *  - A few constructors to make it easier to implement<br>
 * <br>
 * The sprite, for the most part, is complete.
 */
public class Sprite extends Entity implements ImageLoader {

    public int index;

    public Image[] frameData;

    public Image loadedImage;

    protected boolean changeLoadedImage(int index) {
        try {
            loadedImage = frameData[index];
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Sprite(File[] images) {
        super();
        // It looks like what you really wanna pass as an argument is a List<File> ???
        index = 0;

        this.hitBox = new Rectangle(getX(), getY(), getWidth(), getHeight());

        this.images = images;

        this.frameData = new Image[this.images.length];
    }

    /**
     * Constructs a Sprite when given the names of the files.<br>
     * <strong>Note: Must be file names found within the resources.images package.</strong>
     */
    public Sprite(String[] names) {
        images = new File[names.length];

        for (int i = 0; i < names.length; i++) {
            images[i] = new File(imgDir + names[i]);
        }

        new Sprite(images);
    }

    @Override
    public boolean update(float dt) {
        return false;
    }

    public Sprite() throws NullPointerException { // might not have a null pointer exception here, I don't know
        if (images != null) {
            new Sprite(images);
        } else {
            throw new NullPointerException("sprite must have image data");
        }
    }
}