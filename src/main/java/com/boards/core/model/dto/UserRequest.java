package com.boards.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserRequest {
    private String displayName;
    private String email;
    private String uid;
}
