package com.github.alphastuff.amazor;

import com.github.alphastuff.amazor.settings.Settings;
import com.github.alphastuff.amazor.windows.PopupWindow;
import com.github.alphastuff.amazor.windows.SettingsWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author PanJohnny
 * @author Domo
 */
public class Amazor {

    private PopupWindow window;
    public Settings settings;
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
//        if(args.length != 0 && args[0].equalsIgnoreCase("src=boot")) {
            new Amazor();
//        } else {
//            new SettingsWindow();
//        }
    }

}
