package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.util.WebUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PopupWindow {
    public PopupRenderPanel popupRenderPanel;
    public PopupWindow(Amazor amazor) {
        Runnable runnable = () -> {
            final JFrame frame = new JFrame();

            ImageIcon exitImg= null;
            ImageIcon minImg= null;
            ImageIcon sImg= null;
            ImageIcon saveImg= null;
            ImageIcon maxImg= null;
            try {
                exitImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/ePic.png").getScaledInstance(25, 25, Image.SCALE_DEFAULT));
                minImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/mPic.png").getScaledInstance(25, 25, Image.SCALE_DEFAULT));
                saveImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/savePic.png").getScaledInstance(18, 18, Image.SCALE_DEFAULT));
                sImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/sPic.png").getScaledInstance(18, 18, Image.SCALE_DEFAULT));
                maxImg = new ImageIcon(WebUtil.readImage("https://raw.githubusercontent.com/AlphaStuff/Amazor/main/src/main/resources/maxPic.png").getScaledInstance(18, 18, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                WebUtil.webError(e);
            }
            JLabel exitPic = new JLabel();
            JLabel minPic = new JLabel();
            JLabel sPic = new JLabel();
            JLabel savePic = new JLabel();
            JLabel maxPic = new JLabel();
            exitPic.setBounds(340,3,25, 25);
            exitPic.setIcon(exitImg);
            minPic.setBounds(315,3,25, 25);
            minPic.setIcon(minImg);
            sPic.setBounds(293,7,18, 18);
            sPic.setIcon(sImg);
            savePic.setBounds(267,7,18, 18);
            savePic.setIcon(saveImg);
            maxPic.setBounds(241,7,18, 18);
            maxPic.setIcon(maxImg);

            int size = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5.2);
            frame.setSize(size,size + 20);
            int x = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth()-size;
            int y = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-size - 20;
            frame.setLocation(x,y);
            popupRenderPanel = new PopupRenderPanel(amazor, frame);
            frame.setUndecorated(true);
            frame.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.6f));
            frame.setLayout(null);
            frame.add(popupRenderPanel);
            frame.setFocusable(true);
            frame.getContentPane().add(minPic);
            frame.getContentPane().add(exitPic);
            frame.getContentPane().add(sPic);
            frame.getContentPane().add(savePic);
            frame.getContentPane().add(maxPic);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
            FrameDragListener frameDragListener = new FrameDragListener(frame);
            frame.addMouseListener(frameDragListener);
            frame.addMouseMotionListener(frameDragListener);
            frame.requestFocus();

            exitPic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            minPic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.setState(Frame.ICONIFIED); // minimize program
                }
            });

            sPic.addMouseListener(new MouseAdapter() {
                @SuppressWarnings("InstantiationOfUtilityClass")
                @Override
                public void mouseClicked(MouseEvent e) {
                    new SettingsWindow(amazor);
                }
            });

            savePic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.getName().endsWith(".png");
                        }

                        @Override
                        public String getDescription() {
                            return "Portable Graphics Format file (.png)";
                        }
                    });
                    if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        try {
                            file.createNewFile();
                            ImageIO.write(toBufferedImage(popupRenderPanel.image), "PNG", file);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

        };
        SwingUtilities.invokeLater(runnable);
    }

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
            assert mouseDownCompCoords != null;
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
