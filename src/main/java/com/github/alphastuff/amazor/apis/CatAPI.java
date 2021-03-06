package com.github.alphastuff.amazor.apis;

import com.github.alphastuff.amazor.util.WebUtil;

import java.awt.*;
import java.io.IOException;

public class CatAPI {
    public static final String ENDPOINT = "https://cataas.com/cat";
    private CatAPI() {}

    public static Image getRandomImage() {
        try {
            return WebUtil.readImage(ENDPOINT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image getRandomImage(String text, Color color) {
        try {
            return WebUtil.readImage(WebUtil.addPropertyToRequest(ENDPOINT + "/says/"+text, "color", color));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
