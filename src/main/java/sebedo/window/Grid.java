package sebedo.window;

import java.awt.*;
import java.awt.image.BufferedImage;

import static sebedo.image.ImageLoader.loadImage;

public class Grid {
    private static Grid gridSingleton;

    private static final BufferedImage grid = loadImage("src/main/resources/ui/grid.png");
    public static double gridSize = 2;
    private static Image scaledGrid = grid.getScaledInstance((int) (600 * (gridSize / 6)), (int) (600 * (gridSize / 6)), 0);

    private Grid() { }

    public static Grid get() {
        if (gridSingleton == null) {
            gridSingleton = new Grid();
        }

        return gridSingleton;
    }

    public static Image getScaledGrid() {
        return scaledGrid;
    }

    public void setGridSize(int size) {
        gridSize = size;
        scaledGrid = grid.getScaledInstance((int) (600 * (gridSize / 6)), (int) (600 * (gridSize / 6)), 0);
    }
}
