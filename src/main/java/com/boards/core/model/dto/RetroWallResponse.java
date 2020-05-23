package com.boards.core.model.dto;

import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import lombok.Data;

@Data
public class RetroWallResponse {
    private RetroWall retroWall;
    private WallStyle wallStyle;
    private StickyNoteStyle stickyNoteStyle;
}
