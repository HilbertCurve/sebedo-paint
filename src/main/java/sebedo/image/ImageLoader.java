package sebedo.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageLoader {

    default BufferedImage[] loadImages(String[] filenames) {
        File[] files = new File[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            files[i] = new File(filenames[i]);
        }

        return loadImages(files);
    }

    default BufferedImage[] loadImages(File[] files) {
        BufferedImage[] images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                images[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    default Image loadImage(String filename) {
        return new ImageIcon(filename).getImage();
    }
}
