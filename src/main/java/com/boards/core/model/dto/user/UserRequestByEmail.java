package com.boards.core.model.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRequestByEmail {
    @NotNull
    @Email
    private String email;
}
