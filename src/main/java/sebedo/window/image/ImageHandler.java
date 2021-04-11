package sebedo.window.image;

import javax.swing.*;
import java.awt.*;


public class ImageHandler {

    public Dimension d = new Dimension();

    public void setSurfaceSize(Image image, JPanel panel) {
        d.width = image.getWidth(null);
        d.height = image.getHeight(null);
        panel.setPreferredSize(d);
    }

    public void setSurfaceSize(int x, int y, JPanel panel) {
        d.width = x;
        d.height = y;
        panel.setPreferredSize(d);
    }

    public void drawImage(Graphics g, Image image, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, x, y, null);
    }
}
