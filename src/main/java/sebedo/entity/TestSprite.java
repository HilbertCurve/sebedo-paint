package sebedo.entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Debug test sprite
 */
public class TestSprite extends Sprite {
    public TestSprite() {
        super(new String[]{
                "cookieDollarOne.png",
                "cookieDollarTen.png",
                "cookieDollarHundred.png"
        });

        frameData = loadImages(images);
        super.changeLoadedImage(index);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch(e.getKeyChar()) {
                    case 'w': changeLocation(0, -10);
                    case 's': changeLocation(0, 10);
                    case 'a': changeLocation(-10, 0);
                    case 'd': changeLocation(10, 0);
                    case '1': changeLoadedImage(0);
                    case '2': changeLoadedImage(1);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.setLocation(0, 0);
        this.setBounds(0,0,this.getWidth(),this.getHeight());
    }
}
