package sebedo.shape;

import java.awt.*;
import java.awt.geom.Line2D;

public class SebedoLine extends Line2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoLine() {
        new Line2D.Double();
    }

    @Override
    public BasicStroke getStroke(SebedoGraphic shape) {
        return this.shape.stroke;
    }

}
