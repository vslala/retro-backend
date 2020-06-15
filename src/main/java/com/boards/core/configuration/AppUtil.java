package com.boards.core.configuration;

import com.boards.core.model.entities.retroboard.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public static <T> List<T> convertToList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}
