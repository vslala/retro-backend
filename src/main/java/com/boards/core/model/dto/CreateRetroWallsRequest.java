package com.boards.core.model.dto;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateRetroWallsRequest {
    @NotNull
    String retroBoardId;
    @NotNull
    List<CreateRetroWallRequest> walls;
}
