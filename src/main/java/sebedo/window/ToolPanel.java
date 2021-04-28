package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ToolPanel extends JPanel implements ChangeListener {
    private static ToolPanel toolPanel;

    private static final JSlider jSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 1);
    private static final JColorChooser jColorChooser = new JColorChooser();

    static {
        jSlider.addChangeListener(ToolPanel.get());

        jColorChooser.setBounds(0, 200, 300, 200);
    }

    private ToolPanel() {
        super(new BorderLayout());

        this.setPreferredSize(new Dimension(300, 600));
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(0, 1));
        this.add(jSlider);
        this.add(jColorChooser);
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
