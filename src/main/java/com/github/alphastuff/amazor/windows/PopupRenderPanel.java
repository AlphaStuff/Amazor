package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.apis.CatAPI;
import com.github.alphastuff.amazor.apis.ShibeAPI;
import com.github.alphastuff.amazor.util.Checks;
import com.github.alphastuff.amazor.util.ContentManager;
import com.github.alphastuff.amazor.util.WebUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class PopupRenderPanel extends JPanel {
    private Amazor amazor;
    public Image image;
    private ContentManager manager;
    private int size = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5.6);
    public PopupRenderPanel(Amazor amazor, JFrame frame) {
        this.amazor = amazor;

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

        setLocation(frame.getWidth()/28, frame.getHeight()/12);

        this.manager = new ContentManager(amazor.settings);

        updateImage();

    }

    public void updateImage() {
        if(manager.isImageEnabled()) {
            boolean custom = false;
            if(manager.isAdvancedImageEnabled() && manager.getImageAdvancedUrl()!=null) {
                try {
                    image = WebUtil.readImage(manager.getImageAdvancedUrl());
                    custom = true;
                } catch (IOException e) {

                }
            }

            if(!custom) {
                if(manager.getImageType()==null)
                    image = CatAPI.getRandomImage();
                switch (Checks.translateType(manager.getImageType())){
                    case 0:
                        image = CatAPI.getRandomImage();
                        break;
                    case 1:
                        image = ShibeAPI.getRandomImage();
                        break;
                    case 2:
                        if(new Random().nextBoolean())
                            image = CatAPI.getRandomImage();
                        else
                            image = ShibeAPI.getRandomImage();
                }
            }
            assert image != null;
            if (image.getWidth(null) < size) updateImage();
            float maxW = (float) size / image.getWidth(null);
            float maxH = (float) size / image.getHeight(null);
            double scaling = Math.min(maxW, maxH);
            int resizedW = (int) (image.getHeight(null) * scaling);
            int resizedH = (int) (image.getHeight(null) * scaling);
            image = image.getScaledInstance(resizedW, resizedH, Image.SCALE_SMOOTH);
            setSize(resizedW, resizedH);
        }

    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        if(manager.isImageEnabled()) {
            g.drawImage(image, 0, 0, null);
        }
        g.dispose();
    }
}
