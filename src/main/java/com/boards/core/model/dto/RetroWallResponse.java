package com.boards.core.model.dto;

import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RetroWallResponse {
    private String retroBoardId;
    private String wallId;
    private String title;
    private WallStyleResponse style;
    private boolean sortCards;
}
