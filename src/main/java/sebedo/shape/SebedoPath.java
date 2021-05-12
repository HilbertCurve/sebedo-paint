package sebedo.shape;

import java.awt.*;
import java.awt.geom.Path2D;

public class SebedoPath extends Path2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoPath() {
        new Double();
    }

    @Override
    public BasicStroke getStroke(SebedoGraphic s) {
        return this.shape.stroke;
    }
}
