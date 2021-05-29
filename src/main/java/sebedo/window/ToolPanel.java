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

    private static ToolPanel toolPanel;

    private static int width, height;

    private static final JLabel sliderLabel = new JLabel("Stroke Thickness:");

    public static final JButton colorChooserButton = new JButton("Choose Color");
    public static final JButton fillChooserButton = new JButton("Choose Fill");
    public static final JButton bgColorChooserButton = new JButton("Choose Bg. Color");

    public static final JSlider strokeWeightSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 21, 1);
    public static final JComboBox<String> toolSelector = new JComboBox<>(Tools.toolNames);

    private static final ToolPanelItem sliderPanel = new ToolPanelItem(0, 0, sliderLabel, strokeWeightSlider);

    private static final ToolPanelItem colorChooserPanel = new ToolPanelItem(0, 50, new JLabel("Colors:"), colorChooserButton, fillChooserButton, bgColorChooserButton);
    private static final ToolPanelItem toolSelectorPanel = new ToolPanelItem(0, 50, new JLabel("Selected Tool:"), toolSelector);

    /* Make sure to initialize layout after components. */
    private static final GroupLayout toolLayout;

    static {
        width = 300;
        height = 600;

        toolLayout = new GroupLayout(ToolPanel.get());

        sliderLabel.setPreferredSize(new Dimension(width - 13, 10));

        strokeWeightSlider.addChangeListener(ToolPanel.get());
        strokeWeightSlider.setMinorTickSpacing(1);
        strokeWeightSlider.setMajorTickSpacing(10);
        strokeWeightSlider.setPaintTicks(true);
        strokeWeightSlider.setSnapToTicks(true);
        strokeWeightSlider.setPreferredSize(new Dimension(sliderPanel.getWidth(), 30));

        colorChooserButton.addActionListener(ToolPanel.get());
        fillChooserButton.addActionListener(ToolPanel.get());
        bgColorChooserButton.addActionListener(ToolPanel.get());

        toolSelector.setMaximumSize(new Dimension(width, 20));
        toolSelector.setEnabled(true);
        toolSelector.addActionListener(ToolPanel.get());

        toolLayout.setAutoCreateGaps(true);
        toolLayout.setAutoCreateContainerGaps(true);
        toolLayout.setHorizontalGroup(
            toolLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(sliderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(colorChooserPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toolSelectorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        toolLayout.setVerticalGroup(
            toolLayout.createSequentialGroup()
                .addComponent(sliderPanel)
                .addComponent(colorChooserPanel)
                .addComponent(toolSelectorPanel)
        );

        ToolPanel.get().setLayout(toolLayout);
    }
    private ToolPanel() {
        super(toolLayout);

        this.setPreferredSize(new Dimension(width, height));
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
        if (e.getSource() == colorChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Color",
                    this.getBackground());
            if(newColor != null){
                PaintPanel.color = newColor;
            }
        } else if (e.getSource() == fillChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Fill Color",
                    this.getBackground());
            if(newColor != null){
                PaintPanel.fillColor = newColor;
            }
        } else if (e.getSource() == bgColorChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    this.getBackground());
            if(newColor != null){
                PaintPanel.bgColor = newColor;
            }
        } else if (e.getSource() == toolSelector) {
            PaintPanel.get().setSelectedTool(Objects.requireNonNull(Tools.valueOf((String) toolSelector.getSelectedItem())));
        }
    }
}
