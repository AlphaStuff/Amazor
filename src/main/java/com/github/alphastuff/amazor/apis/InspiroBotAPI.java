package com.github.alphastuff.amazor.apis;

import com.github.alphastuff.amazor.util.WebUtil;

import java.awt.*;
import java.io.IOException;

public class InspiroBotAPI {
    public static final String ENDPOINT = "https://inspirobot.me/api?generate=true";
    private InspiroBotAPI() {}
    public static Image getRandomImage() {
        try {
            return WebUtil.readImage(WebUtil.readRequestFromUrl(ENDPOINT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CatAPI.getRandomImage("InspiroBotAPI is not working", Color.BLACK);
    }
}
