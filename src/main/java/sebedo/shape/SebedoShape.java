package sebedo.shape;

import sebedo.window.PaintPanel;

import java.awt.*;

public class SebedoShape {
    BasicStroke stroke;
    Color color;
    Color fill;

    public SebedoShape() {
        // set shape properties to what is currently in PaintPanel.
        stroke = new BasicStroke(PaintPanel.strokeWeight, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        color = PaintPanel.color;
        fill = PaintPanel.fillColor;
    }
}
