package sebedo.window;

import java.awt.*;

public interface SebedoShape {
    BasicStroke getStroke(SebedoShape shape);
    void setStroke(int width);
}

