package com.boards.core.model.dto.retroboard;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.retroboard.StickyNoteStyle;
import lombok.Data;

@Data
public class StickyNoteStyleResponse {
    private String backgroundColor;
    private String likeBtnPosition;
    private String textColor;

    public static StickyNoteStyleResponse createResponse(StickyNoteStyle stickyNoteStyle) {
        var stickyNoteStyleResponse = new StickyNoteStyleResponse();
        stickyNoteStyleResponse.setLikeBtnPosition(stickyNoteStyle.getLikeBtnPosition());
        stickyNoteStyleResponse.setTextColor(stickyNoteStyle.getTextColor());
        stickyNoteStyleResponse.setBackgroundColor(stickyNoteStyle.getBackgroundColor());
        return stickyNoteStyleResponse;
    }

    public StickyNoteStyle createStickyNoteWallStyle() {
        StickyNoteStyle stickyNoteStyle = new StickyNoteStyle();
        stickyNoteStyle.setStickyNoteStyleId(AppUtil.uniqId());
        stickyNoteStyle.setBackgroundColor(backgroundColor);
        stickyNoteStyle.setLikeBtnPosition(likeBtnPosition);
        stickyNoteStyle.setTextColor(textColor);
        return stickyNoteStyle;
    }
}
