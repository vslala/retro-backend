package com.boards.core.configuration;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    private AppUtil() {}

    public static String uniqId() {
        String uniqId = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(uniqId.getBytes());
    }
}
