package com.github.alphastuff.amazor.windows;
import com.github.alphastuff.amazor.Amazor;
import com.github.alphastuff.amazor.apis.*;
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
    public Image lastImage;
    private ContentManager manager;
    private QuoteAPI.Quote quote;
    private static final int SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5.6);
    public PopupRenderPanel(Amazor amazor, JFrame frame) {
        this.amazor = amazor;

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(lastImage!=null)
                        image = lastImage;
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    updateImage();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setSize(SIZE, SIZE);

        setLocation(frame.getWidth()/28, frame.getHeight()/12);

        this.manager = new ContentManager(amazor.settings);

        updateImage();

    }

    public void updateImage() {
        if(manager.isQuoteEnabled()) {
            quote = QuoteAPI.getRandomQuote();
            System.out.println(quote);
        }
        if(manager.isImageEnabled()) {
            lastImage = image;
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
                    image = CatAPI.getRandomImage("Internal error", Color.RED);
                getImageFromID(Checks.translateType(manager.getImageType()));

            }
            assert image != null;
            if (image.getWidth(null) < SIZE) updateImage();
            float maxW = (float) SIZE / image.getWidth(null);
            float maxH = (float) SIZE / image.getHeight(null);
            double scaling = Math.min(maxW, maxH);
            int reSIZEdW = (int) (image.getHeight(null) * scaling);
            int reSIZEdH = (int) (image.getHeight(null) * scaling);
            image = image.getScaledInstance(reSIZEdW, reSIZEdH, Image.SCALE_DEFAULT);
            setSize(reSIZEdW, reSIZEdH);
        }

    }

    public void getImageFromID(int id) {
        switch (id) {
            case 0:
                image = CatAPI.getRandomImage();
                break;
            case 1:
                image = ShibeAPI.getRandomImage();
                break;
            case 2:
                image = InspiroBotAPI.getRandomImage();
                break;
            case 3:
                image = DogAPI.getRandomImage();
                break;
            case 4:
                getImageFromID(new Random().nextInt(4));
        }
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        if(manager.isImageEnabled()) {
            g.drawImage(image, 0, 0, null);
        }
        if(manager.isQuoteEnabled()) {
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            g.setColor(Color.white);
            g.drawString(quote.toString(), 10, 10);
        }
        g.dispose();
    }
}
