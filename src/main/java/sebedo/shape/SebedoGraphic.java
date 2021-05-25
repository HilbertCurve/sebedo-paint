package sebedo.shape;

import java.awt.*;
import java.lang.reflect.Field;

public interface SebedoGraphic {
    BasicStroke getStroke();
    void setStroke(BasicStroke stroke);
    Color getColor();
    void setColor(Color color);
    Color getFill();
    void setFill(Color color);
}

