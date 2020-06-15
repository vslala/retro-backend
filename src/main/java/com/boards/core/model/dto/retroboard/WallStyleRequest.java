package com.boards.core.model.dto.retroboard;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.retroboard.RetroWall;
import com.boards.core.model.entities.retroboard.StickyNoteStyle;
import com.boards.core.model.entities.retroboard.WallStyle;
import lombok.Data;

@Data
public class WallStyleRequest {
    CreateStickyNoteStyleRequest stickyNote;

    public WallStyle createWallStyle(RetroWall retroWall, StickyNoteStyle stickyNoteWallStyle) {
        WallStyle wallStyle = new WallStyle();
        wallStyle.setWallId(retroWall.getWallId());
        wallStyle.setWallStyleId(AppUtil.uniqId());
        return wallStyle;
    }
}
