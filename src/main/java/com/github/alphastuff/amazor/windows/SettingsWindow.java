package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsWindow {
    private static SettingsWindow window;
    private SettingsWindow() {
        Runnable runnable = () -> {
            final JFrame frame = new JFrame() {
                @Override
                public void dispose() {
                    super.dispose();
                    window = null;
                }
            };

            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            frame.setLayout(null);
            frame.setFocusable(true);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setAlwaysOnTop(true);

            frame.setVisible(true);

            PopupWindow.FrameDragListener frameDragListener = new PopupWindow.FrameDragListener(frame);
            frame.addMouseListener(frameDragListener);
            frame.addMouseMotionListener(frameDragListener);
            frame.requestFocus();

        };
        SwingUtilities.invokeLater(runnable);
    }
    public static SettingsWindow getInstance() {
        if(window==null) {
            window = new SettingsWindow();
            return window;
        }
        return window;
    }
}



