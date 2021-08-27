package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupWindow {
    public PopupRenderPanel popupRenderPanel;
    public PopupWindow(Amazor amazor) {
        Runnable runnable = () -> {
            final JFrame frame = new JFrame();

            // add buttons
            ImageIcon exitImg = new ImageIcon(new ImageIcon("src/main/resources/ePic.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
            ImageIcon minImg = new ImageIcon(new ImageIcon("src/main/resources/mPic.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
            JLabel exitPic = new JLabel();
            JLabel minPic = new JLabel();
            exitPic.setBounds(340,5,18, 18);
            exitPic.setIcon(exitImg);
            minPic.setBounds(315,5,18, 18);
            minPic.setIcon(minImg);


            int size = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5.2);
            frame.setSize(size,size);
            int x = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth()-size;
            int y = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-size;
            frame.setLocation(x,y);

            popupRenderPanel = new PopupRenderPanel(amazor, frame);
            
            frame.setUndecorated(true);
            frame.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.6f));
            frame.setLayout(null);
            frame.add(popupRenderPanel);
            frame.setFocusable(true);
            frame.getContentPane().add(minPic);
            frame.getContentPane().add(exitPic);
            frame.setVisible(true);

            FrameDragListener frameDragListener = new FrameDragListener(frame);
            frame.addMouseListener(frameDragListener);
            frame.addMouseMotionListener(frameDragListener);

            frame.requestFocus();

            exitPic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0); // THIS DOESN'T SHUTDOWN WHOLE PROGRAM NEEDS TO BE FIXED ------------------------------------
                }
            });


            minPic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.setState(Frame.ICONIFIED); // minimize program
                }
            });
        };
        SwingUtilities.invokeLater(runnable);
    }

    // Drag frame around screen with mouse
    public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
}
