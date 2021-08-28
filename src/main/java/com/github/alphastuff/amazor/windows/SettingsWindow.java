package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.util.Checks;
import com.github.alphastuff.amazor.util.ContentManager;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class SettingsWindow{
    private static SettingsWindow window;

    private Amazor amazor;
    private SettingsWindow(Amazor amazor) {
        this.amazor = amazor;
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

            JCheckBox imageEnabled = new JCheckBox("Enable image showing", amazor.settings.getBoolean(ContentManager.IMAGE));
            imageEnabled.addActionListener(e -> {
                amazor.settings.set(ContentManager.IMAGE, imageEnabled.isSelected());
            });
            frame.add(imageEnabled);

            JList<String> imageType = new JList<>(new String[]{"cat", "shibe", "random"});
            imageType.setSelectedIndex(Checks.translateType(amazor.settings.getString(ContentManager.IMAGE_TYPE)));
            imageType.addListSelectionListener(e -> amazor.settings.set(ContentManager.IMAGE_TYPE, imageType.getSelectedValue()));

            frame.add(imageType);

            HintTextField imageAdvancedUrl = new HintTextField("Enter url");
            imageAdvancedUrl.setText(amazor.settings.getString(ContentManager.IMAGE_ADVANCED_URL));
            imageAdvancedUrl.setOnTextChange(() -> {
                amazor.settings.set(ContentManager.IMAGE_ADVANCED_URL, imageAdvancedUrl.getText());
            });

            JCheckBox imageAdvanced = new JCheckBox("Enable image advanced", amazor.settings.getBoolean(ContentManager.IMAGE_ADVANCED));
            imageAdvanced.addActionListener(e -> {
                amazor.settings.set(ContentManager.IMAGE_ADVANCED, imageAdvanced.isSelected());
            });

            frame.add(imageAdvanced);
            frame.add(imageAdvancedUrl);

            frame.setLayout(new FlowLayout());
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

    public static SettingsWindow getInstance(Amazor amazor) {
        if(window==null) {
            window = new SettingsWindow(amazor);
            return window;
        }
        return window;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    class HintTextField extends JTextField implements FocusListener {

        private final String hint;
        private boolean showingHint;
        private Runnable change;

        public HintTextField(final String hint) {
            super(hint);
            this.hint = hint;
            this.showingHint = true;
            super.addFocusListener(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
                showingHint = false;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(hint);
                showingHint = true;
            }
        }

        @Override
        public void repaint() {
            String a;
            try {
                a = getText();
            }catch (NullPointerException e) {
                a = "";
            }
            if(!a.isBlank()&&!showingHint&&!a.isEmpty()&&a.startsWith("https") | a.startsWith("http")) {
                if(change==null) return;
                change.run();
            }
            super.repaint();
        }

        public void setOnTextChange(Runnable runnable) {
            change = runnable;
        }

        @Override
        public String getText() {
            return showingHint ? "" : super.getText();
        }

        @Override
        public void setText(String t) {
            if(showingHint) showingHint = false;
            super.setText(t);
        }
    }
}



