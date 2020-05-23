package com.boards.core.model.dto;

import com.boards.core.configuration.AppConfig;
import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.NoteStyle;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.User;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Data
public class CreateNoteRequest {
    private String noteId;
    private String retroBoardId;
    private String wallId;
    private String noteText;
    private CreateStickyNoteStyleRequest style;
    private List<String> likedBy;
    private List<String> createdBy;


    public Note createNote() {
        var note = new Note();
        note.setNoteId(AppUtil.uniqId());
        note.setNoteText(noteText);
        note.setRetroBoardId(retroBoardId);
        note.setWallId(wallId);
        note.setCreatedBy(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        return note;
    }

    public NoteStyle createNoteStyle(String noteId, CreateStickyNoteStyleRequest style) {
        var noteStyle = new NoteStyle();
        noteStyle.setNoteStyleId(noteId);
        noteStyle.setBackgroundColor(style.getBackgroundColor());
        noteStyle.setLikeBtnPosition(style.getLikeBtnPosition());
        noteStyle.setTextColor(style.getTextColor());
        return noteStyle;
    }
}
