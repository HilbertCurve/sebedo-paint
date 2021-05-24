package sebedo.shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SebedoRectangle extends Rectangle2D.Double implements SebedoGraphic {
    SebedoShape shape = new SebedoShape();

    public SebedoRectangle() {
        new Rectangle2D.Double();
    }

    public SebedoRectangle(int x, int y, int width, int height) {
        new Rectangle2D.Double(x, y, width, height);
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
    public Color getColor() {
        return this.shape.color;
    }

    @Override
    public void setColor(Color color) {
        this.shape.color = color;
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
