package com.github.alphastuff.amazor.windows;

import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.apis.CatAPI;
import com.github.alphastuff.amazor.apis.ShibeAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class PopupRenderPanel extends JPanel {
    private Amazor amazor;
    public Image image;
    private int size = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5.6);
    public PopupRenderPanel(Amazor amazor, JFrame frame) {
        this.amazor = amazor;
        updateImage();
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_A) {
                    updateImage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setSize(size, size);
        setLocation(10, 10);

    }

    public void updateImage() {
        if(new Random().nextBoolean()) {
            image = ShibeAPI.getRandomImage();
        } else
            image = CatAPI.getRandomImage("cute");

        assert image != null;
        if(image.getWidth(null)<size) updateImage();
        float maxW = (float) size/image.getWidth(null);
        float maxH = (float) size/image.getHeight(null);
        double scaling;
        if (maxW<maxH) {
            scaling = maxW;
        } else
            scaling = maxH;
        int resizedW = (int) (image.getHeight(null) * scaling);
        int resizedH = (int) (image.getHeight(null) * scaling);
        image = image.getScaledInstance(resizedW, resizedH, Image.SCALE_SMOOTH);
        setSize(resizedW, resizedH);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }
}
