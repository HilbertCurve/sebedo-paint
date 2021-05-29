package sebedo.shape;

import java.awt.geom.Ellipse2D;

public class SebedoEllipse extends SebedoShape implements SebedoGraphic {
    public SebedoEllipse() {
        createGraphic();
    }

    @Override
    public void createGraphic() {
        awtInstance = new Ellipse2D.Double();
    }
}
