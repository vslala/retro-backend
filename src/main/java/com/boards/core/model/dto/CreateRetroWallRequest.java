package com.boards.core.model.dto;

import lombok.Data;

@Data
public class CreateRetroWallRequest {
    private String retroBoardId;
    private String wallId;
    private String title;
    private boolean sortCards;
    private CreateWallStyleRequest style;
}
