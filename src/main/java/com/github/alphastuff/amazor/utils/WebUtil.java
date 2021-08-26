package com.github.alphastuff.amazor.utils;

import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class WebUtil {
    // lock class from being instanced
    private WebUtil() {};
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
            sb.append("&" + name + "=" + value);
        } else {
            sb.append("?" + name + "=" + value);
        }
        return sb.toString();
    }
}
