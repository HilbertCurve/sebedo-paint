package sebedo.shape;

import java.awt.geom.Rectangle2D;

public class SebedoRectangle extends SebedoShape implements SebedoGraphic {
    public SebedoRectangle() {
        createGraphic();
    }

    public SebedoRectangle(int x, int y, int width, int height) {
        new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void createGraphic() {
        awtInstance = new Rectangle2D.Double();
    }
}
