package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import lombok.Data;

@Data
public class CreateWallStyleRequest {
    CreateStickyNoteStyleRequest stickyNote;

    public WallStyle createWallStyle(RetroWall retroWall, StickyNoteStyle stickyNoteWallStyle) {
        WallStyle wallStyle = new WallStyle();
        wallStyle.setWallId(retroWall.getWallId());
        wallStyle.setWallStyleId(AppUtil.uniqId());
        return wallStyle;
    }
}
