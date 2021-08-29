package com.github.alphastuff.amazor;

import com.github.alphastuff.amazor.settings.Settings;
import com.github.alphastuff.amazor.windows.PopupWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author PanJohnny
 * @author Domo
 */

public class Amazor {

    private final PopupWindow window = new PopupWindow(this);
    public Settings settings;
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Amazor()  {
        File path = new File(System.getProperty("user.dir")+"/Amazor/");
        path.mkdir();
        File file = new File(path, "/settings.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            System.exit(500);
        }

        settings = new Settings(file);

        new Thread(this::run).start();
    }

    public void reloadContent() {
        window.popupRenderPanel.updateImage();
    }
    public static void main(String[] args) {
            if(System.getProperty("os.name").startsWith("win")) {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
            new Amazor();
    }

    @SuppressWarnings("CatchMayIgnoreException")
    private void run() {
        while (true) {
            try {
                window.popupRenderPanel.repaint();
            } catch (Exception ex) {

            }
    }
    }
}
