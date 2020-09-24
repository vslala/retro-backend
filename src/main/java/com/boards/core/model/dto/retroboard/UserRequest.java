package com.boards.core.model.dto.retroboard;

import com.boards.core.model.entities.retroboard.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserRequest {
    private String displayName;
    private String email;
    private String uid;
    private boolean isEmailVerified;

    public User createUser() {
        var user = new User();
        user.setUid(uid);
        user.setDisplayName(displayName);
        user.setEmail(email);
        user.setEmailVerified(isEmailVerified);
        return user;
    }
}
