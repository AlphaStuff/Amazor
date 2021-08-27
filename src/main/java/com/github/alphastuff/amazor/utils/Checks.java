package com.github.alphastuff.amazor.utils;

public class Checks {
    private Checks() {}

    public static void checkForNull(Object obj) {
        if(obj == null) {
            throw new NullPointerException("value must be nonnull");
        }
    }
}
