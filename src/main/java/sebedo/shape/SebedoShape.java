package sebedo.shape;

import sebedo.window.PaintPanel;

import java.awt.*;

public class SebedoShape {
    BasicStroke stroke;
    Color color;
    Color fill;
    public Shape awtInstance;

    public SebedoShape() {
        // set shape properties to what is currently in PaintPanel.
        stroke = new BasicStroke(PaintPanel.strokeWeight, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        color = PaintPanel.color;
        fill = PaintPanel.fillColor;
    }

    public BasicStroke getStroke() {
        return this.stroke;
    }
    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getFill() {
        return fill;
    }
    public void setFill(Color fill) {
        this.fill = fill;
    }
    public Shape getAwtInstance() {
        return awtInstance;
    }
}
