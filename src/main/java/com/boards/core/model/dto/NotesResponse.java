package com.boards.core.model.dto;

import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.NoteStyle;
import com.boards.core.model.entities.Vote;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotesResponse {
    List<NoteResponse> notes = new ArrayList<>();

    public static NotesResponse createResponse(List<Note> notes, Iterable<NoteStyle> noteStyles, List<List<Vote>> votesForEachNoteId) {
        NotesResponse response = new NotesResponse();
        for (Note note : notes) {
            for (NoteStyle noteStyle : noteStyles) {
                if (note.getNoteId().equals(noteStyle.getNoteStyleId())) {
                    var singleResponse = new NoteResponse();
                    singleResponse.setNoteId(note.getNoteId());
                    singleResponse.setRetroBoardId(note.getRetroBoardId());
                    singleResponse.setNoteText(note.getNoteText());
                    singleResponse.setWallId(note.getWallId());
                    singleResponse.setStyle(noteStyle);
                    singleResponse.setCreatedBy(note.getCreatedBy());
                    singleResponse.setLikedBy(
                            votesForEachNoteId.stream()
                                    .flatMap(votes -> votes.stream())
                                    .filter(vote -> vote.getItemId().equals(note.getNoteId()))
                                    .collect(Collectors.toList()));
                    response.add(singleResponse);
                }
            }
        }

        return response;
    }

    private void add(NoteResponse singleResponse) {
        notes.add(singleResponse);
    }
}
