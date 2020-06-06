package com.boards.core.model.dto.retroboard;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.retroboard.Note;
import com.boards.core.model.entities.retroboard.NoteStyle;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.retroboard.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (Objects.isNull(likedBy)) return Collections.emptyList();

        List<Vote> votes = likedBy.stream().filter(likedBy -> !Objects.isNull(likedBy.getUid()) && !likedBy.getUid().isEmpty())
                .collect(Collectors.toList()).stream()
                .map(likedBy -> Vote.newInstance(noteId, likedBy.getUid(), "NOTE"))
                .collect(Collectors.toList());

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
