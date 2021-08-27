package com.github.alphastuff.amazor.util;

import com.github.alphastuff.amazor.settings.Settings;

public class ContentManager {
    public static final String IMAGE = "image";
    public static final String IMAGE_TYPE = "image.type";
    public static final String IMAGE_ADVANCED = "image.advanced";
    public static final String IMAGE_ADVANCED_URL = "image.advanced.url";
    private Settings settings;
    public ContentManager(Settings settings) {
        this.settings = settings;
        reload();
    }

    public void reload(){
        settings.reload();
    }

    public boolean isImageEnabled() {
        return settings.getBoolean(IMAGE);
    }

    public String getImageType() {
        return settings.getString(IMAGE_TYPE);
    }


    public boolean isAdvancedImageEnabled() {
        return settings.getBoolean(IMAGE_ADVANCED);
    }

    public String getImageAdvancedUrl() {
        return settings.getString(IMAGE_ADVANCED_URL);
    }
}
