package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.util.Checks;
import com.github.alphastuff.amazor.util.ContentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsWindow{
    private static SettingsWindow window;

    private Amazor amazor;
    private SettingsWindow(Amazor amazor) {
        this.amazor = amazor;
        ContentManager manager = new ContentManager(amazor.settings);
        Runnable runnable = () -> {
            final JFrame frame = new JFrame() {
                @Override
                public void dispose() {
                    super.dispose();
                    window = null;
                }
            };

            frame.setSize(200, 250);
            frame.setLocationRelativeTo(null);

            JCheckBox imageEnabled = new JCheckBox("Enable image showing", manager.isImageEnabled());
            imageEnabled.addActionListener(e -> amazor.settings.set(ContentManager.IMAGE, imageEnabled.isSelected()));
            frame.add(imageEnabled);

            JList<String> imageType = new JList<>(new String[]{"cat", "shibe", "random"});
            imageType.setSelectedIndex(Checks.translateType(manager.getImageType()));
            imageType.addListSelectionListener(e -> {
                amazor.settings.set(ContentManager.IMAGE_TYPE, imageType.getSelectedValue());
                amazor.reloadContent();
            });

            frame.add(imageType);

            HintTextField imageAdvancedUrl = new HintTextField("Enter url");
            imageAdvancedUrl.setText(manager.getImageAdvancedUrl());
            imageAdvancedUrl.setOnTextChange(() -> {
                amazor.settings.set(ContentManager.IMAGE_ADVANCED_URL, imageAdvancedUrl.getText());
                amazor.reloadContent();
            });

            JCheckBox imageAdvanced = new JCheckBox("Enable image advanced", manager.isAdvancedImageEnabled());
            imageAdvanced.addActionListener(e -> {
                amazor.settings.set(ContentManager.IMAGE_ADVANCED, imageAdvanced.isSelected());
                amazor.reloadContent();
            });

            frame.add(imageAdvanced);
            frame.add(imageAdvancedUrl);

            frame.setLayout(null);
            frame.setFocusable(true);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setAlwaysOnTop(true);

            // ----------------------------------------------------------------------------
            ImageIcon exitImg = new ImageIcon(new ImageIcon("src/main/resources/ePic.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            JLabel exitPic = new JLabel();
            exitPic.setBounds(170,3,25, 25);
            exitPic.setIcon(exitImg);
            frame.setUndecorated(true);
            frame.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.6f));
            imageAdvanced.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.3f));
            imageAdvancedUrl.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.3f));
            imageEnabled.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.3f));
            imageType.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.3f));
            imageType.setForeground(Color.WHITE);
            imageAdvanced.setForeground(Color.WHITE);
            imageAdvancedUrl.setForeground(Color.WHITE);
            imageEnabled.setForeground(Color.WHITE);

            imageEnabled.setBounds(10,20,180,15);
            imageAdvanced.setBounds(10,50,180,15);
            imageAdvancedUrl.setBounds(10,80,180,15);
            imageType.setBounds(10,110,180,100);

            imageEnabled.setOpaque(false);
            imageAdvanced.setOpaque(false);
            imageAdvancedUrl.setOpaque(false);
            imageType.setOpaque(false);


            frame.getContentPane().add(exitPic);
            frame.setVisible(true);

            PopupWindow.FrameDragListener frameDragListener = new PopupWindow.FrameDragListener(frame);
            frame.addMouseListener(frameDragListener);
            frame.addMouseMotionListener(frameDragListener);
            frame.requestFocus();

            exitPic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.dispose();
                }
            });


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



