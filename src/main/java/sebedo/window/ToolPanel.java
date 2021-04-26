package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ToolPanel extends JPanel implements ChangeListener {
    private static ToolPanel toolPanel;

    private static final JSlider jSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 1);

    static {
        jSlider.setBounds(0, 0, ToolPanel.get().getWidth(), ToolPanel.get().getHeight());
        jSlider.addChangeListener(ToolPanel.get());
    }

    private ToolPanel() {
        this.setBackground(Color.WHITE);
        this.add(jSlider);
    }

    public static ToolPanel get() {
        if (toolPanel == null) {
            toolPanel = new ToolPanel();
        }

        return toolPanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jSlider && jSlider.getValueIsAdjusting()) {
            PaintPanel.strokeWeight = jSlider.getValue();
        }
    }
}
