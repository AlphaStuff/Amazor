package com.github.alphastuff.amazor.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class WebUtil {
    // lock class from being instanced
    private WebUtil() {}
    public static String readRequestFromUrl(String web) throws IOException {
        URL url = new URL(web);
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(url.openStream());
        StringBuilder sb = new StringBuilder();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        return sb.toString();
    }

    public static BufferedImage readImage(String web) throws IOException {
        URL url = new URL(web);
        return ImageIO.read(url);
    }

    public static String addPropertyToRequest(String endpoint, String name, Object value) {
        StringBuilder sb = new StringBuilder(endpoint);
        if (endpoint.contains("?")){
            sb.append("&").append(name).append("=").append(value);
        } else {
            sb.append("?").append(name).append("=").append(value);
        }
        return sb.toString();
    }

    public static void webError(IOException ex) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "This problem is probably caused by limited wifi network, or no connection to internet", "Error: "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        System.exit(2);
    }
}
