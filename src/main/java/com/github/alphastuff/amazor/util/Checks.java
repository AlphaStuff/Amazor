package com.github.alphastuff.amazor.util;

public class Checks {
    private Checks() {}

    public static void checkForNull(Object obj) {
        if(obj == null) {
            throw new NullPointerException("value must be nonnull");
        }
    }

    public static int translateType(String type) {
        return switch (type.toLowerCase().trim()) {
            case "cat" -> 0;
            case "shibe" -> 1;
            case "inspirobot" -> 2;
            case "dog" -> 3;
            default -> 4;
        };
    }
}
