package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * JPanel responsible for various actions.
 */
public class ToolPanel extends JPanel implements ChangeListener, Actions {
    private static ToolPanel toolPanel;

    public static class ToolPanelItem extends JPanel {
        public ToolPanelItem(int x, int y, JComponent... components) {
            this.setLocation(new Point(x, y));

            BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(layout);

            if (components != null) {
                int width = 0;
                int height = 0;
                for (JComponent o : components) {
                    this.add(o);
                    width = Math.max(width, o.getWidth());
                    height += o.getHeight();
                }

                this.setSize(new Dimension(width, height));
            } else {
                this.setSize(new Dimension(50, 50));
            }
        }
    }

    private static final JLabel sliderLabel = new JLabel("Stroke Thickness:");
    private static final JSlider strokeWeightSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 21, 1);
    private static final ToolPanelItem sliderPanel = new ToolPanelItem(100, 0, sliderLabel, strokeWeightSlider);

    private static final ToolPanelItem colorChooserPanel = null; // FIXME

    private static final JComboBox<JButton> toolSelector = new JComboBox<>();

    /* Make sure to initialize layout after components. */
    private static final GroupLayout toolLayout = new GroupLayout(ToolPanel.get());

    // TODO: finish static initializer (primarily, finish group layout)
    static {
        strokeWeightSlider.addChangeListener(ToolPanel.get());
        strokeWeightSlider.setMinorTickSpacing(1);
        strokeWeightSlider.setMajorTickSpacing(10);
        strokeWeightSlider.setPaintTicks(true);
        strokeWeightSlider.setSnapToTicks(true);

        sliderPanel.setBackground(Color.WHITE);

        toolLayout.setAutoCreateGaps(true);
        toolLayout.setAutoCreateContainerGaps(true);
        toolLayout.setHorizontalGroup(
            toolLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(sliderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        toolLayout.setVerticalGroup(
            toolLayout.createSequentialGroup()
                .addGroup(toolLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sliderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        ToolPanel.get().setLayout(toolLayout);
    }

    private ToolPanel() {
        super(new BorderLayout());

        this.setPreferredSize(new Dimension(300, 600));
        this.setBackground(Color.WHITE);
    }

    public static ToolPanel get() {
        if (toolPanel == null) {
            toolPanel = new ToolPanel();
        }

        return toolPanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == strokeWeightSlider && strokeWeightSlider.getValueIsAdjusting()) {
            PaintPanel.strokeWeight = strokeWeightSlider.getValue();
        }
    }

    @Override
    public void doAction(long action) {

    }
}
