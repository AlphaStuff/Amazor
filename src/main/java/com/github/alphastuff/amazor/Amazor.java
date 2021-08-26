package com.github.alphastuff.amazor;

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

/**
 * @author PanJohnny
 * @author Domo
 */
public class Amazor extends Canvas implements Runnable{

    private Thread thread;
    private boolean running;
    private Image dog;
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
                    refreshDog();
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
        dog = ShibeAPI.getRandomImage();
    }

    public void render() {
        if(getBufferStrategy() == null) {
            createBufferStrategy(3);
            return;
        }
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
        g.drawString("Focus: "+frame.hasFocus(), 80, 80);
        g.drawImage(dog, 100, 100, null);
        g.dispose();
        bs.show();
    }
}
