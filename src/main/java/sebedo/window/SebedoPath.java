package sebedo.window;

import java.awt.*;
import java.awt.geom.Path2D;

public class SebedoPath extends Path2D.Double implements SebedoShape {
    BasicStroke stroke;

    public SebedoPath() {
        stroke = new BasicStroke(PaintPanel.strokeWeight);
        new Path2D.Double();
    }

    @Override
    public BasicStroke getStroke(SebedoShape s) {
        return this.stroke;
    }
    @Override
    public void setStroke(int width) {
        stroke = new BasicStroke(width);
    }
}
