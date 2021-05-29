package sebedo.shape;

import java.awt.geom.Path2D;

public class SebedoPath extends SebedoShape implements SebedoGraphic {
    public SebedoPath() {
        createGraphic();
    }

    @Override
    public void createGraphic() {
        this.awtInstance = new Path2D.Double();
    }
}
