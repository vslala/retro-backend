package com.boards.core.model.dto;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;

@Data
public class CreateRetroBoardRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer maxLikes;
}
