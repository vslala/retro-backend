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

    public WallStyleResponse createResponse(StickyNoteStyleResponse stickyNoteStyleResponse) {
        return new WallStyleResponse(stickyNoteStyleResponse);
    }
}
