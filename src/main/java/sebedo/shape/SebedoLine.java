package sebedo.shape;

import java.awt.*;
import java.awt.geom.Line2D;

public class SebedoLine extends Line2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoLine() {
        new Line2D.Double();
    }

    @Override
    public BasicStroke getStroke() {
        return this.shape.stroke;
    }

    @Override
    public Color getColor() {
        return this.shape.color;
    }

    @Override
    public Color getFill() {
        return this.shape.fill;
    }
}
