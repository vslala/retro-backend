package com.boards.core.model.dto.templates;

import com.boards.core.model.entities.retroboard.NoteStyle;
import lombok.Data;

@Data
public class TemplateNoteRequest {
    private String noteText;
    private NoteStyle noteStyle;
}
