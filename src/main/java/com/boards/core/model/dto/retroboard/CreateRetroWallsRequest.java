package com.boards.core.model.dto.retroboard;

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
    List<RetroWallRequest> walls;
}
