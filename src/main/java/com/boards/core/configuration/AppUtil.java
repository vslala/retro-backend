package com.boards.core.configuration;

import com.boards.core.model.entities.retroboard.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    private AppUtil() {}

    public static String uniqId() {
        String uniqId = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(uniqId.getBytes());
    }

    public static String anonymousEmail(String anonymousUid) {
        return  anonymousUid.concat("@retro.com");
    }

    public static String anonymousDisplayName(String anonymousUid) {
        return "anonymous_user_".concat(anonymousUid);
    }

    public static User getLoggedInUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
