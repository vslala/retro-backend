package com.boards.core.services;

import com.boards.core.model.dto.CreateNoteRequest;
import com.boards.core.model.dto.NotesResponse;
import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.NoteStyle;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.repositories.NoteRepository;
import com.boards.core.model.repositories.NoteStyleRepository;
import com.boards.core.model.repositories.StickyNoteStyleRepository;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Service
public class StickyNoteService {

    private NoteRepository noteRepository;
    private NoteStyleRepository noteStyleRepository;

    public StickyNoteService(NoteRepository noteRepository, NoteStyleRepository noteStyleRepository) {
        this.noteRepository = noteRepository;
        this.noteStyleRepository = noteStyleRepository;
    }

    public URI createNoteForWall(CreateNoteRequest noteRequest) {
        Note persistedNote = noteRepository.save(noteRequest.createNote());
        noteStyleRepository.save(noteRequest.createNoteStyle(persistedNote.getNoteId(), noteRequest.getStyle()));
        URI uri = null;
        try {
            var uriBuilder = new URIBuilder("/retro-board/walls/notes");
            uriBuilder.addParameter("retroBoardId", noteRequest.getRetroBoardId());
            uriBuilder.addParameter("wallId", noteRequest.getWallId());
            uri = URI.create(uriBuilder.build().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Bad URI! Message:" + e.getMessage(), e);
        }
        return uri;
    }

    public NotesResponse getNotesForWall(String wallId) {
        List<Note> notes = noteRepository.findAllByWallId(wallId);
        return buildNotesResponse(notes);
    }

    public NotesResponse getNotesForBoard(String retroBoardId) {
        List<Note> notes = noteRepository.findAllByRetroBoardId(retroBoardId);
        return buildNotesResponse(notes);
    }

    private NotesResponse buildNotesResponse(List<Note> notes) {
        List<String> noteIds = notes.stream().map(note -> note.getNoteId()).collect(Collectors.toList());
        Iterable<NoteStyle> noteStyles = noteStyleRepository.findAllById(noteIds);
        return NotesResponse.createResponse(notes, noteStyles);
    }
}
