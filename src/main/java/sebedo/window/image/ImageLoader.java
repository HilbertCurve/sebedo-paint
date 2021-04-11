package sebedo.window.image;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public interface ImageLoader {

    default Image[] loadImages(String[] filenames) {
        Image[] images = new Image[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            images[i] = new ImageIcon(filenames[i]).getImage();
        }
        return images;
    }

    default Image[] loadImages(File[] files) {
        Image[] images = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            images[i] = new ImageIcon(files[i].getAbsolutePath()).getImage();
        }
        return images;
    }

    default Image loadImage(String filename) {
        return new ImageIcon(filename).getImage();
    }
}
