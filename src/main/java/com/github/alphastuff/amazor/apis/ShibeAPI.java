package com.github.alphastuff.amazor.apis;

import com.github.alphastuff.amazor.util.WebUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ShibeAPI {
    private static final String ENDPOINT = "https://shibe.online/api/shibes";
    // lock class from being instanced
    private ShibeAPI() {}

    public static Image getRandomImage() {
        return getRandomImages(1).get(0);
    }

    public static ArrayList<Image> getRandomImages(int count) {
        ArrayList<Image> images = new ArrayList<>();
        try {
            JsonArray urls = JsonParser.parseString(WebUtil.readRequestFromUrl(WebUtil.addPropertyToRequest(ENDPOINT, "count", count))).getAsJsonArray();
            urls.forEach(e -> {
                try {
                    images.add(WebUtil.readImage(e.getAsString()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }
}
