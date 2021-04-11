package sebedo.window;

import javax.swing.*;
import java.awt.*;

public class DebugPanel extends JPanel {
    JButton button = new JButton("Hello");

    public DebugPanel() {
        add(button);
        setPreferredSize(new Dimension(300, 300));
        setBackground(new Color(20, 20, 0));
    }
}
