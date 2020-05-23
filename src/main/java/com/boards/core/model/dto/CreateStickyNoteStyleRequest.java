package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import lombok.Data;

@Data
public class CreateStickyNoteStyleRequest {
    private String backgroundColor;
    private String likeBtnPosition;
    private String textColor;

    public static StickyNoteStyle createStickyNote(String wallId, WallStyle wallStyle, CreateStickyNoteStyleRequest stickyNoteStyleRequest) {
        StickyNoteStyle stickyNoteStyle = new StickyNoteStyle();
        stickyNoteStyle.setStickyNoteStyleId(AppUtil.uniqId());
        stickyNoteStyle.setWallStyleId(wallStyle.getWallStyleId());
        stickyNoteStyle.setBackgroundColor(stickyNoteStyleRequest.getBackgroundColor());
        stickyNoteStyle.setLikeBtnPosition(stickyNoteStyleRequest.getLikeBtnPosition());
        stickyNoteStyle.setTextColor(stickyNoteStyleRequest.getTextColor());
        return stickyNoteStyle;
    }

}
