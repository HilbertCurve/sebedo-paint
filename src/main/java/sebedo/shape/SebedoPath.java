package sebedo.shape;

import java.awt.*;
import java.awt.geom.Path2D;

public class SebedoPath extends Path2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoPath() {
        new Double();
    }

    @Override
    public BasicStroke getStroke() {
        return this.shape.stroke;
    }

    @Override
    public void setStroke(BasicStroke stroke) {
        this.shape.stroke = stroke;
    }

    @Override
    public void setColor(Color color) {
        this.shape.color = color;
    }

    @Override
    public Color getColor() {
        return this.shape.color;
    }

    @Override
    public Color getFill() {
        return this.shape.fill;
    }

    @Override
    public void setFill(Color color) {
        this.shape.color = color;
    }
}
