package com.boards.core.model.dto;

import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.NoteStyle;
import com.boards.core.model.entities.Vote;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
public class NoteResponse {
    private String noteId;
    private String noteText;
    private String retroBoardId;
    private String wallId;
    private String createdBy;
    private NoteStyle style;
    private List<Vote> likedBy;

    public static NoteResponse createResponse(Note persistedNote, NoteStyle noteStyle, Iterable<Vote> persistedVote) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setNoteId(persistedNote.getNoteId());
        noteResponse.setNoteText(persistedNote.getNoteText());
        noteResponse.setRetroBoardId(persistedNote.getRetroBoardId());
        noteResponse.setWallId(persistedNote.getWallId());
        noteResponse.setCreatedBy(persistedNote.getCreatedBy());
        noteResponse.setStyle(noteStyle);
        noteResponse.setLikedBy(StreamSupport.stream(persistedVote.spliterator(), false)
                .collect(Collectors.toList()));
        return noteResponse;
    }
}
