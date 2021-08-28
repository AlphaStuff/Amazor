package com.github.alphastuff.amazor.apis;

import com.github.alphastuff.amazor.util.WebUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class QuoteAPI {
    public static final String ENDPOINT = "https://type.fit/api/quotes";

    public static Quote getRandomQuote() {
        try {
            JsonArray quotes = JsonParser.parseString(WebUtil.readRequestFromUrl(ENDPOINT)).getAsJsonArray();
            JsonObject quote = quotes.get(new Random().nextInt(quotes.size())).getAsJsonObject();
            return new Quote(quote.get("text").getAsString(), quote.get("author").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Quote("Quote api is not working", "PanJohnny");
        }
    }

    public static class Quote {
        private String text;
        private String author;

        public Quote(String text, String author) {
            this.text = text;
            this.author = author;
        }

        public String getText() {
            return text;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return text+"\n-"+author;
        }
    }
}
