package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.StickyNoteStyle;
import lombok.Data;

@Data
public class StickyNoteStyleResponse {
    private String backgroundColor;
    private String likeBtnPosition;
    private String textColor;

    public StickyNoteStyle createStickyNoteWallStyle() {
        StickyNoteStyle stickyNoteStyle = new StickyNoteStyle();
        stickyNoteStyle.setStickyNoteStyleId(AppUtil.uniqId());
        stickyNoteStyle.setBackgroundColor(backgroundColor);
        stickyNoteStyle.setLikeBtnPosition(likeBtnPosition);
        stickyNoteStyle.setTextColor(textColor);
        return stickyNoteStyle;
    }
}
