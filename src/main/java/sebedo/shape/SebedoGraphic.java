package sebedo.shape;

import java.awt.*;
import java.lang.reflect.Field;

// TODO: add the rest of the setters, and fix
public interface SebedoGraphic {
    default BasicStroke getStroke() throws NoSuchFieldException {
        Class<SebedoGraphic> sgc = SebedoGraphic.class;
        try {
            sgc.getField("shape");
        } catch (NoSuchFieldException e) {
            // this should never happen
        }
        return null;
    }
    void setStroke(BasicStroke stroke);
    Color getColor();
    void setColor(Color color);
    Color getFill();
    void setFill(Color color);
}

