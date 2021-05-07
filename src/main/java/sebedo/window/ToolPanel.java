package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * JPanel responsible for various actions.
 */
public class ToolPanel extends JPanel implements ChangeListener, ActionListener {
    private static ToolPanel toolPanel;

    public static class ToolPanelItem extends JPanel {

        public ToolPanelItem(int x, int y, JLabel label, JComponent... components) {
            this.setLocation(new Point(x, y));

            BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);

            this.setLayout(bl);
            this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));

            this.add(label);

            if (components != null) {
                int width = 0;
                int height = 0;
                for (JComponent o : components) {
                    this.add(o);
                    width = Math.max(width, o.getWidth());
                    height += o.getHeight();
                }

                this.setMaximumSize(new Dimension(width, height));
            } else {
                this.setSize(new Dimension(50, 50));
            }
        }
    }
    private static final JLabel sliderLabel = new JLabel("Stroke Thickness:");

    private static final JSlider strokeWeightSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 21, 1);
    private static final JComboBox<String> toolSelector = new JComboBox<>(Tools.toolNames);

    private static final ToolPanelItem sliderPanel = new ToolPanelItem(0, 0, sliderLabel, strokeWeightSlider);

    private static final ToolPanelItem colorChooserPanel = null; // FIXME
    private static final ToolPanelItem toolSelectorPanel = new ToolPanelItem(0, 50, new JLabel("Selected Tool:"), toolSelector);
    /* Make sure to initialize layout after components. */

    private static final GroupLayout toolLayout = new GroupLayout(ToolPanel.get());
    // TODO: finish static initializer (primarily, finish group layout)

    static {
        sliderLabel.setPreferredSize(new Dimension(200, 10));

        strokeWeightSlider.addChangeListener(ToolPanel.get());
        strokeWeightSlider.setMinorTickSpacing(1);
        strokeWeightSlider.setMajorTickSpacing(10);
        strokeWeightSlider.setPaintTicks(true);
        strokeWeightSlider.setSnapToTicks(true);
        strokeWeightSlider.setPreferredSize(new Dimension(sliderPanel.getWidth(), 30));

        toolSelector.setMaximumSize(new Dimension(100, 20));
        toolSelector.setEnabled(true);
        toolSelector.addActionListener(ToolPanel.get());

        sliderPanel.setBackground(Color.WHITE);
        toolSelectorPanel.setBackground(Color.WHITE);

        toolLayout.setAutoCreateGaps(true);
        toolLayout.setAutoCreateContainerGaps(true);
        toolLayout.setHorizontalGroup(
            toolLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(sliderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toolSelectorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        toolLayout.setVerticalGroup(
            toolLayout.createSequentialGroup()
                .addComponent(sliderPanel)
                .addComponent(toolSelectorPanel)
        );

        ToolPanel.get().setLayout(toolLayout);
    }
    private ToolPanel() {
        super(toolLayout);

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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolSelector) {
            PaintPanel.get().setSelectedTool(Objects.requireNonNull(Tools.valueOf((String) toolSelector.getSelectedItem())));
        }
    }
}
