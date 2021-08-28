package com.github.alphastuff.amazor.util;

public class Checks {
    private Checks() {}

    public static void checkForNull(Object obj) {
        if(obj == null) {
            throw new NullPointerException("value must be nonnull");
        }
    }

    public static int translateType(String type) {
        switch (type.toLowerCase().trim()) {
            case "cat":
                return 0;
            case "shibe":
                return 1;
            case "inspirobot":
                return 2;
            case "dog":
                return 3;
            default:
                return 4;
        }
    }
}
