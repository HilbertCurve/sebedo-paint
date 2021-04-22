package sebedo.window;

import javax.swing.*;

public class ToolPanel extends JPanel {
    private static ToolPanel toolPanel;

    private ToolPanel() {

    }

    public static ToolPanel get() {
        if (toolPanel == null) {
            toolPanel = new ToolPanel();
        }

        return toolPanel;
    }
}
