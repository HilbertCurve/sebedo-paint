package sebedo.shape;

import sebedo.window.PaintPanel;

import java.awt.*;

public class SebedoShape {
    BasicStroke stroke;
    Color color;
    Color fill;

    public SebedoShape() {
        stroke = new BasicStroke(PaintPanel.strokeWeight, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        color = PaintPanel.color;
        fill = PaintPanel.fillColor;
    }
}
