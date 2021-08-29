package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.util.Checks;
import com.github.alphastuff.amazor.util.ContentManager;
import com.github.alphastuff.amazor.util.WebUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class SettingsWindow{
    private static int windows = 0;

    public static final String[] TYPES = new String[]{"cat", "shibe", "inspirobot", "dog", "random"};
    public static final Color ELEMENT_BACKGROUND = new Color(0.1f, 0.1f, 0.1f, 0.3f);
    public static final Color FRAME_BACKGROUND = new Color(0.1f, 0.1f, 0.1f, 0.5f);
    public static final Color FOREGROUND_COLOR = Color.WHITE;

    public SettingsWindow(Amazor amazor) {
        if(windows!=0)
            return;
        ContentManager manager = new ContentManager(amazor.settings);
        Runnable runnable = () -> {
            final JFrame frame = new JFrame() {
                @Override
                public void dispose() {
                    super.dispose();
                    windows--;
                }
            };

            frame.setSize(200, 250);
            frame.setLocationRelativeTo(null);

            JCheckBox imageEnabled = new JCheckBox("Enable image showing", manager.isImageEnabled());
            imageEnabled.addActionListener(e -> amazor.settings.set(ContentManager.IMAGE, imageEnabled.isSelected()));
            frame.add(imageEnabled);

            JCheckBox imageSlideShow = new JCheckBox("Enable slide show", manager.isImageEnabled());
            imageEnabled.addActionListener(e -> amazor.settings.set(ContentManager.IMAGE, imageSlideShow.isSelected()));
            frame.add(imageSlideShow);

            JList<String> imageType = new JList<>(TYPES);
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

            ImageIcon edgeImg = null;
            ImageIcon exitImg = null;
            try {
                edgeImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/edgePic.png").getScaledInstance(200, 250, Image.SCALE_DEFAULT));
                exitImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/ePic.png").getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                WebUtil.webError(e);
            }

            JLabel edgePic = new JLabel();
            JLabel exitPic = new JLabel();
            frame.getContentPane().add(edgePic);
            edgePic.setBounds(0,0,200, 250);
            edgePic.setIcon(edgeImg);
            exitPic.setBounds(167,9,25, 25);
            exitPic.setIcon(exitImg);
            frame.setUndecorated(true);
            frame.setBackground(FRAME_BACKGROUND);

            imageAdvanced.setBackground(ELEMENT_BACKGROUND);
            imageAdvancedUrl.setBackground(ELEMENT_BACKGROUND);
            imageEnabled.setBackground(ELEMENT_BACKGROUND);
            imageType.setBackground(ELEMENT_BACKGROUND);
            imageSlideShow.setBackground(ELEMENT_BACKGROUND);

            imageType.setForeground(FOREGROUND_COLOR);
            imageAdvanced.setForeground(FOREGROUND_COLOR);
            imageAdvancedUrl.setForeground(FOREGROUND_COLOR);
            imageEnabled.setForeground(FOREGROUND_COLOR);
            imageSlideShow.setForeground(FOREGROUND_COLOR);
            imageEnabled.setBounds(10,20,153,15);
            imageSlideShow.setBounds(10,50,160,15);
            imageAdvanced.setBounds(10,80,160,15);
            imageAdvancedUrl.setBounds(15,110,150,15);
            imageType.setBounds(15,140,110,70);

            imageEnabled.setOpaque(false);
            imageAdvanced.setOpaque(false);
            imageAdvancedUrl.setOpaque(false);
            imageType.setOpaque(false);
            imageSlideShow.setOpaque(false);

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

    static class HintTextField extends JTextField implements FocusListener {

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



