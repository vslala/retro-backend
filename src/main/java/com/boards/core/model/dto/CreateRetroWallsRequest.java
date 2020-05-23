package com.boards.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.annotations.NotNull;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRetroWallsRequest {
    @NotNull
    String retroBoardId;
    @NotNull
    List<CreateRetroWallRequest> walls;
}
