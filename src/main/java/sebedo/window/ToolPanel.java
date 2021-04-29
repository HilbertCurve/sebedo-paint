package sebedo.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * JPanel responsible for various actions.
 */
public class ToolPanel extends JPanel implements ChangeListener {
    private static ToolPanel toolPanel;

    // FiXmE pLeAsE
    public class ToolPanelItem {

    }

    private static final JPanel sliderPanel = new JPanel();
    private static final JLabel sliderLabel = new JLabel();
    private static final JSlider strokeWeightSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 1);

    private static final JPanel colorChooserPanel = new JPanel();
    private static final JColorChooser colorChooser = new JColorChooser();

    private static final JPanel toolSelectorPanel = new JPanel();
    private static final JComboBox toolSelector = new JComboBox();

    /* Make sure to initialize layout after components. */
    private static final GroupLayout layout = new GroupLayout(ToolPanel.get());

    // TODO: finish static initializer (mainly, finish group layout)
    static {
        strokeWeightSlider.addChangeListener(ToolPanel.get());

        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        sliderPanel.setPreferredSize(new Dimension(60, 5));
        sliderPanel.add(strokeWeightSlider);
        sliderPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        colorChooser.setPreferredSize(new Dimension(100, 200));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(sliderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(colorChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sliderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(colorChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ToolPanel.get().setLayout(layout);
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
}
