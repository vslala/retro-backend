package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.NoteStyle;
import com.boards.core.model.entities.User;
import com.boards.core.model.entities.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NoteRequest {
    private String noteId;
    private String retroBoardId;
    private String wallId;
    private String noteText;
    private CreateStickyNoteStyleRequest style;
    private List<UserRequest> likedBy;
    private String createdBy;


    public Note createNote() {
        var note = new Note();
        note.setNoteId(AppUtil.uniqId());
        note.setNoteText(noteText);
        note.setRetroBoardId(retroBoardId);
        note.setWallId(wallId);
        note.setCreatedBy(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUid());
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

    public List<Vote> createVotes() {
        List<Vote> votes = new ArrayList<>();
        if (Objects.isNull(likedBy)) return votes;
        likedBy.forEach(likeBy -> votes.add(Vote.newInstance(noteId, likeBy.getUid(), "NOTE")));

        return votes;
    }

    public Note updateNote() {
        Note note = createNote();
        note.setNoteId(noteId); // have to keep the same id while updating
        return note;
    }

    public List<Vote> updateVote() {
        return createVotes();
    }

    public NoteStyle updateNoteStyle(String noteId, CreateStickyNoteStyleRequest style) {
        NoteStyle noteStyle = createNoteStyle(noteId, style);
        noteStyle.setNoteStyleId(style.getNoteStyleId());
        return noteStyle;
    }
}
