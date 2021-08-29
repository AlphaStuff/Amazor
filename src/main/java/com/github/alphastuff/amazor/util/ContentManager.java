package com.github.alphastuff.amazor.util;

import com.github.alphastuff.amazor.settings.Settings;

public record ContentManager(Settings settings) {
    public static final String IMAGE = "image";
    public static final String IMAGE_TYPE = "image.type";
    public static final String IMAGE_ADVANCED = "image.advanced";
    public static final String IMAGE_ADVANCED_URL = "image.advanced.url";
    public static final String IMAGE_SLIDE_SHOW = "image.slide.show";

    public void reload() {
        settings.reload();
    }

    public boolean isImageEnabled() {
        reload();
        return settings.getBoolean(IMAGE);
    }

    public String getImageType() {
        reload();
        return settings.getString(IMAGE_TYPE);
    }

    public boolean isAdvancedImageEnabled() {
        reload();
        return settings.getBoolean(IMAGE_ADVANCED);
    }

    public String getImageAdvancedUrl() {
        reload();
        return settings.getString(IMAGE_ADVANCED_URL);
    }

    public String ImageSlideShow() {
        reload();
        return settings.getString(IMAGE_SLIDE_SHOW);
    }

}
