package com.github.alphastuff.amazor.apis;

import com.github.alphastuff.amazor.util.WebUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.io.IOException;

public class DogAPI {
    public static final String ENDPOINT = "https://dog.ceo/api";
    private DogAPI() {}

    public static Image getRandomImage() {
        try {
            JsonObject url = JsonParser.parseString(WebUtil.readRequestFromUrl(ENDPOINT+"/breeds/image/random")).getAsJsonObject();
            return WebUtil.readImage(url.get("message").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CatAPI.getRandomImage("Dog API doesn't work :(", Color.BLACK);
    }
}
