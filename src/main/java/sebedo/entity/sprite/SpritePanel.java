package sebedo.entity.sprite;

import sebedo.window.image.ImageHandler;
import sebedo.window.image.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SpritePanel extends JPanel implements ImageLoader {
    // this holds all the graphics that's put on the frame.

    public ArrayList<Sprite> sprites = new ArrayList<>();
    ImageHandler h = new ImageHandler();
    TestSprite ts = new TestSprite();

    public SpritePanel() {
        ts.setFocusable(true);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ts.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        sprites.add(ts);
        h.setSurfaceSize(sprites.get(0).loadedImage, this);

        this.add(sprites.get(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        h.setSurfaceSize(sprites.get(0).loadedImage, this);
        h.drawImage(g, sprites.get(0).loadedImage, sprites.get(0).getX(), sprites.get(0).getY());
    }
}
