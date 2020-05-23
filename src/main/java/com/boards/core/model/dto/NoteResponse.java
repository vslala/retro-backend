package com.boards.core.model.dto;

import com.boards.core.model.entities.NoteStyle;
import lombok.Data;

@Data
public class NoteResponse {
    private String noteId;
    private String noteText;
    private String retroBoardId;
    private String wallId;
    private String createdBy;
    private NoteStyle style;
}
