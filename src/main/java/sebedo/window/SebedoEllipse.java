package sebedo.window;

import java.awt.*;
import java.awt.geom.Ellipse2D;

// TODO: finish sebedo shapes
public class SebedoEllipse extends Ellipse2D.Double implements SebedoShape {
    BasicStroke stroke;

    @Override
    public BasicStroke getStroke(SebedoShape shape) {
        return null;
    }

    @Override
    public void setStroke(int width) {

    }
}
