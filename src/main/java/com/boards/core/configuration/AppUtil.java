package com.boards.core.configuration;

import java.util.Base64;

public class AppUtil {

    private AppUtil() {}

    public static String uniqId() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return Base64.getEncoder().encodeToString(currentTime.getBytes());
    }
}
