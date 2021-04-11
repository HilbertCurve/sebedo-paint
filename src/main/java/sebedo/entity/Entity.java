package sebedo.entity;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class is supposed to hold data in regards to loadable objects in various classes. It gets extended by both the
 * Sprite class and something else.
 *
 * What I want from an Entity:<br>
 *  - Position<br>
 *  - Load Status<br>
 *  - Ability to have Listeners<br>
 *
 * TODO: make other Entity-extending classes
 * TODO: fix what I assume to be a layout problem
 *
 */
public abstract class Entity extends JComponent {

    public States loaded;

    public File[] images;

    int x;
    int y;
    private int width;
    private int height;

    public Rectangle hitBox;

    public static final String imgDir = "src/resources/images/";

    protected boolean changeLoadStatus(States state) {
        try {
            loaded = state;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * I don't know what to do with this yet.
     */
    public boolean update() {
        return false;
    }

    /*
     * The following few methods just help encapsulate position and size a little.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int[] getLocationAsIntegers() {
        int[] a = new int[2];
        a[0] = getX();
        a[1] = getY();

        return a;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBounds() {
        super.setBounds(hitBox);
    }

    /**
     * Moves entity to {@code (x,y)}.
     */
    public void setLocation(float x, float y) {
        this.setLocation((int) x, (int) y);
    }

    /**
     * Moves entity {@code (dx,dy)} pixels relative to it's previous location.
     */
    public void changeLocation(float dx, float dy) {
        setLocation(getX() + dx, getY() + dy);
    }

    public Entity() {
        this.setFocusable(true);
        System.out.println(getX());
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }
}