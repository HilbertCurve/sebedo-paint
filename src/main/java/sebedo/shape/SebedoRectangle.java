package sebedo.shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SebedoRectangle extends Rectangle2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoRectangle() {
        new Rectangle2D.Double();
    }

    @Override
    public BasicStroke getStroke(SebedoGraphic shape) {
        return this.shape.stroke;
    }
}
