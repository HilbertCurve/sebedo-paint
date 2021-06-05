package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * JPanel responsible for various actions.
 */
public class ToolPanel extends JPanel implements ChangeListener, ActionListener {
    public static class ToolPanelItem extends JPanel {
        public ToolPanelItem(int prefX, int prefY, JLabel label, JComponent... components) {
            this.setLocation(new Point(prefX, prefY));

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

    public final JSlider strokeWeightSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 21, 1);

    public final JButton colorChooserButton = new JButton("Choose Color");
    public final JButton fillChooserButton = new JButton("Choose Fill");
    public final JButton bgColorChooserButton = new JButton("Choose Bg. Color");

    public final JComboBox<String> toolSelector = new JComboBox<>(Tools.toolNames);

    public final JCheckBox bitArtCheckBox = new JCheckBox("Enable Bit Art Mode:");
    public final JSlider gridSizeSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 21, 12);

    private ToolPanel() {
        int width = 300;
        int height = 600;

        /* Make sure to initialize layout after components. */
        GroupLayout toolLayout = new GroupLayout(this);

        /* Initialize ToolPanelItems */
        ToolPanelItem strokeWeightPanel = new ToolPanelItem(0, 0, new JLabel("Stroke Thickness:"), strokeWeightSlider);
        ToolPanelItem colorChooserPanel = new ToolPanelItem(0, 50, new JLabel("Colors:"), colorChooserButton, fillChooserButton, bgColorChooserButton);
        ToolPanelItem toolSelectorPanel = new ToolPanelItem(0, 100, new JLabel("Selected Tool:"), toolSelector);
        ToolPanelItem bitArtTogglePanel = new ToolPanelItem(0, 150, new JLabel("Bit-Art Mode:"), bitArtCheckBox, gridSizeSlider);

        /* Add listeners to data-related objects */
        strokeWeightSlider.addChangeListener(this);
        colorChooserButton.addActionListener(this);
        fillChooserButton.addActionListener(this);
        bgColorChooserButton.addActionListener(this);
        toolSelector.addActionListener(this);
        bitArtCheckBox.addActionListener(this);
        gridSizeSlider.addChangeListener(this);

        /* Configure data-related objects */
        strokeWeightSlider.setMinorTickSpacing(1);
        strokeWeightSlider.setMajorTickSpacing(10);
        strokeWeightSlider.setPaintTicks(true);
        strokeWeightSlider.setSnapToTicks(true);
        strokeWeightSlider.setPreferredSize(new Dimension(strokeWeightPanel.getWidth(), 30));

        toolSelector.setPreferredSize(new Dimension(width, 20));
        toolSelector.setEnabled(true);

        /* Configure toolLayout */
        toolLayout.setAutoCreateGaps(true);
        toolLayout.setAutoCreateContainerGaps(true);

        toolLayout.setHorizontalGroup(
                toolLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(strokeWeightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(colorChooserPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toolSelectorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bitArtTogglePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        toolLayout.setVerticalGroup(
                toolLayout.createSequentialGroup()
                        .addComponent(strokeWeightPanel)
                        .addComponent(colorChooserPanel)
                        .addComponent(toolSelectorPanel)
                        .addComponent(bitArtTogglePanel)
        );

        this.setLayout(toolLayout);

        this.setPreferredSize(new Dimension(width, height));
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
        } else if (e.getSource() == gridSizeSlider) {
            Grid.get().setGridSize(gridSizeSlider.getValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colorChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Color",
                    this.getBackground());
            if (newColor != null) {
                PaintPanel.color = newColor;
            }
        } else if (e.getSource() == fillChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Fill Color",
                    this.getBackground());
            if (newColor != null) {
                PaintPanel.fillColor = newColor;
            }
        } else if (e.getSource() == bgColorChooserButton) {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    this.getBackground());
            if (newColor != null) {
                PaintPanel.bgColor = newColor;
            }
        } else if (e.getSource() == toolSelector) {
            PaintPanel.get().setSelectedTool(Objects.requireNonNull(Tools.valueOf((String) toolSelector.getSelectedItem())));
        } else if (e.getSource() == bitArtCheckBox) {
            PaintPanel.get().setIsBitArtDraw(bitArtCheckBox.isSelected());
        }
    }
}
