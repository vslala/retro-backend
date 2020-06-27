package com.boards.core.model.dto.retroboard;

import lombok.Data;

@Data
public class WallStyleResponse {
    StickyNoteStyleResponse stickyNote;

    public WallStyleResponse() {
    }

    public WallStyleResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        this.stickyNote = stickyNoteStyleResponse;
    }

    public static WallStyleResponse createWallStyleResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        var wallStyleResponse = new WallStyleResponse();
        wallStyleResponse.setStickyNote(stickyNoteStyleResponse);
        return wallStyleResponse;
    }

    public WallStyleResponse createResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        return new WallStyleResponse(stickyNoteStyleResponse);
    }
}
