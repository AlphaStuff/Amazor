package com.github.alphastuff.amazor.settings;

import com.github.alphastuff.amazor.utils.Checks;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import org.ietf.jgss.GSSContext;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.*;

public class Settings {

    private File file;
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

    public Number getNumber(@Nonnull String key) {
        Checks.checkForNull(key);
        JsonElement element = json.get(key);
        return element == null ? -1 : element.getAsNumber();
    }

    public String getString(@Nonnull String key) {
        Checks.checkForNull(key);
        JsonElement element = json.get(key);
        return element == null ? null : element.getAsString();
    }

    public boolean getBoolean(@Nonnull String key) {
        Checks.checkForNull(key);
        JsonElement element = json.get(key);
        return element != null && element.getAsBoolean();
    }

    public void set(String key, Object value)  {
        json.addProperty(key, value.toString());
        try {
            new FileWriter(file).write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
