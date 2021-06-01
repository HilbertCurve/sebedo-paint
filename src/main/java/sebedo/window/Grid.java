package sebedo.window;

import sebedo.shape.SebedoRectangle;

import java.awt.*;
import java.util.ArrayList;

public class Grid {
    ArrayList<SebedoRectangle> grid = new ArrayList<>();
    int pixelSize;
    final Color[] colors = new Color[] {
            Color.GRAY,
            Color.WHITE
    };

    public Grid(int pixelSize) {
        this.pixelSize = pixelSize;
        this.initGrid(pixelSize);
    }

    public void initGrid(int pixelSize) {
        PaintPanel.strokeWeight = 0;
        Color color = colors[0];
        for (int i = 0; i < PaintFrame.width; i += pixelSize) {
            if (color == colors[0]) {
                color = colors[1];
            } else {
                color = colors[0];
            }
            for (int j = 0; j < PaintFrame.height; j += pixelSize) {
                PaintPanel.fillColor = color;
                grid.add(new SebedoRectangle(i, j, i + pixelSize, j + pixelSize));
                if (color == colors[0]) {
                    color = colors[1];
                } else {
                    color = colors[0];
                }
            }
        }

        System.out.println(grid.size());
    }

    public ArrayList<SebedoRectangle> getGrid() {
        return grid;
    }
}
