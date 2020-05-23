package com.boards.core.model.dto;

import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import lombok.Data;

@Data
public class WallStyleResponse {
    StickyNoteStyleResponse stickyNote;

    public WallStyleResponse() {
    }

    public WallStyleResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        this.stickyNote = stickyNoteStyleResponse;
    }

    public WallStyleResponse createResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        return new WallStyleResponse(stickyNoteStyleResponse);
    }
}
