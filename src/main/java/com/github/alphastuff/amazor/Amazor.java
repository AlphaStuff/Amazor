package com.github.alphastuff.amazor;

import com.github.alphastuff.amazor.apis.CatAPI;
import com.github.alphastuff.amazor.apis.ShibeAPI;
import com.panjohnny.LogFormat;
import com.panjohnny.LogProperty;
import com.panjohnny.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author PanJohnny
 * @author Domo
 */
public class Amazor extends Canvas implements Runnable{

    private Thread thread;
    private boolean running;
    private Image image;
    private JFrame frame;
    public Amazor() {
        frame = new JFrame("Test stuff");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        this.addKeyListener(new KeyListener() {
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
        frame.setVisible(true);
        frame.requestFocus();
        thread = new Thread(this);
        start();
    }
    public static void main(String[] args) {
        if(args.length != 0 && args[0].equalsIgnoreCase("src=boot")) {
            new Amazor();
        } else {
            // do other stuff because the source of start is not boot
            System.out.println("not booted open settings");
        }
    }

    public synchronized void start() {
        running = true;
        thread.start();
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
    @Override
    public void run() {
        updateImage();
        while(running) {
            render();
        }

        try {
            thread.join(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateImage() {
        if(new Random().nextBoolean()) {
            image = ShibeAPI.getRandomImage();
        } else {
            image = CatAPI.getRandomImage();
        }
    }

    public void render() {
        if(getBufferStrategy() == null) {
            createBufferStrategy(3);
            return;
        }
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 100, 100, null);
        g.dispose();
        bs.show();
    }
}
