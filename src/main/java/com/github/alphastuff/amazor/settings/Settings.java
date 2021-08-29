package com.github.alphastuff.amazor.settings;

import com.github.alphastuff.amazor.util.Checks;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nonnull;
import java.io.*;

public class Settings {

    private final File file;
    private JsonObject json;
    public Settings(File file) {
        this.file = file;
        reload();
    }

    public void reload() {
        try {
            JsonElement el = JsonParser.parseReader(new FileReader(file));
            if(!el.isJsonObject()) {
                System.err.println("Settings file corrupted");
                return;
            }
            json = el.getAsJsonObject();
        } catch (FileNotFoundException e) {
            System.err.println("Settings file not found");
        }
    }

    public String getString(@Nonnull String key) {
        Checks.checkForNull(key);
        JsonElement element = json.get(key);
        return element == null ? "cat" : element.getAsString();
    }

    public boolean getBoolean(@Nonnull String key) {
        Checks.checkForNull(key);
        JsonElement element = json.get(key);
        return element != null && element.getAsBoolean();
    }

    public void set(String key, Object value)  {
        value = value == null? "null" : value;
        json.addProperty(key, value.toString());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(new Gson().toJson(json));
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
