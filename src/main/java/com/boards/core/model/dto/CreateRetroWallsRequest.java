package com.boards.core.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateRetroWallsRequest {
    List<CreateRetroWallRequest> walls;
}
