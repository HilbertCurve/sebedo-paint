package sebedo.window;

import sebedo.window.image.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class FrameInit extends JFrame implements ImageLoader {

    protected void makeFrame(JPanel jPanel, String name) {
        add(jPanel);
        JScrollPane s = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(s);
        setLayout(new FlowLayout());
        pack();

        setTitle(name);
        setSize(super.getWidth(), super.getHeight());
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(null);
    }
}
