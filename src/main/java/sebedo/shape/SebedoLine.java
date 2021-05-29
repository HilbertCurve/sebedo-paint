package sebedo.shape;

import java.awt.geom.Line2D;

public class SebedoLine extends SebedoShape implements SebedoGraphic {
    public SebedoLine() {
        createGraphic();
    }

    public SebedoLine(int x1, int y1, int x2, int y2) {
        this.awtInstance = new Line2D.Double(x1, y1, x2, y2);
    }

    @Override
    public void createGraphic() {
        this.awtInstance = new Line2D.Double();
    }
}
