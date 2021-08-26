package com.github.alphastuff.amazor;

import com.github.alphastuff.amazor.apis.CatAPI;
import com.github.alphastuff.amazor.apis.ShibeAPI;
import com.github.alphastuff.amazor.windows.PopupRenderPanel;
import com.github.alphastuff.amazor.windows.PopupWindow;
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
public class Amazor {

    private PopupWindow window;
    public Amazor() {
        window = new PopupWindow(this);

        new Thread(() -> {
            while (true) {
                try {
                    window.popupRenderPanel.repaint();
                } catch (Exception e) {}
            }
        }).start();
    }
    public static void main(String[] args) {
        //if(args.length != 0 && args[0].equalsIgnoreCase("src=boot")) {
            new Amazor();
        //} else {
            // do other stuff because the source of start is not boot
            //System.out.println("not booted open settings");
            //Toolkit.getDefaultToolkit().beep();
        //}
    }

}
