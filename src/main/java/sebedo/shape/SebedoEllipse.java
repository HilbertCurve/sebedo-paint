package sebedo.shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SebedoEllipse extends Ellipse2D.Double implements SebedoGraphic {
    public SebedoShape shape = new SebedoShape();

    public SebedoEllipse() {
        new Double();
    }

    @Override
    public BasicStroke getStroke(SebedoGraphic shape) {
        return this.shape.stroke;
    }

}
