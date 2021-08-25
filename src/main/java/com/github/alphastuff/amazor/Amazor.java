package com.github.alphastuff.amazor;

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

/**
 * @author PanJohnny
 * @author Domo
 */
public class Amazor extends Canvas implements Runnable{

    private Thread thread;
    private boolean running;
    private Image dog;
    public Amazor() {
        JFrame frame = new JFrame("Test stuff");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F5) {
                    refreshDog();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.setVisible(true);

        thread = new Thread(this);
        start();
    }
    public static void main(String[] args) {
        new Amazor();
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
        refreshDog();
        while(running) {
            render();
        }

        try {
            thread.join(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refreshDog() {
        try {
            File f = WebUtil.readImage(WebUtil.getDog());
            dog = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        if(getBufferStrategy() == null) {
            createBufferStrategy(3);
            return;
        }
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.drawImage(dog, 100, 100, null);
        g.dispose();
        bs.show();
    }
}
