package com.boards.core.model.dto;

import lombok.Data;

@Data
public class CreateStickyNoteStyleRequest {
    private String backgroundColor;
    private String likeBtnPosition;
    private String textColor;
}
