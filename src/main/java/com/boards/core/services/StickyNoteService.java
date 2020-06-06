package com.boards.core.services;

import com.boards.core.model.dto.retroboard.NoteRequest;
import com.boards.core.model.dto.retroboard.NotesResponse;
import com.boards.core.model.entities.retroboard.Note;
import com.boards.core.model.entities.retroboard.NoteStyle;
import com.boards.core.model.entities.retroboard.Vote;
import com.boards.core.model.repositories.retroboard.NoteRepository;
import com.boards.core.model.repositories.retroboard.NoteStyleRepository;
import com.boards.core.model.repositories.retroboard.VoteRepository;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Service
public class StickyNoteService {

    private NoteRepository noteRepository;
    private NoteStyleRepository noteStyleRepository;
    private VoteRepository voteRepository;

    public StickyNoteService(NoteRepository noteRepository, NoteStyleRepository noteStyleRepository, VoteRepository voteRepository) {
        this.noteRepository = noteRepository;
        this.noteStyleRepository = noteStyleRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public URI createNoteForWall(NoteRequest noteRequest) {
        Note persistedNote = noteRepository.save(noteRequest.createNote());
        noteStyleRepository.save(noteRequest.createNoteStyle(persistedNote.getNoteId(), noteRequest.getStyle()));
        URI uri = buildURI(noteRequest.getRetroBoardId(), noteRequest.getWallId());
        return uri;
    }

    private URI buildURI(String retroBoardId, String wallId) {
        URI uri = null;
        try {
            var uriBuilder = new URIBuilder("/retro-board/walls/notes");
            uriBuilder.addParameter("retroBoardId", retroBoardId);
            uriBuilder.addParameter("wallId", wallId);
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
        List<List<Vote>> votesForEachNoteId = noteIds.stream().map(
                noteId -> voteRepository.findAllByItemIdAndType(noteId, String.valueOf(Vote.Type.NOTE)))
                    .collect(Collectors.toList());

        return NotesResponse.createResponse(notes, noteStyles, votesForEachNoteId);
    }

    @Transactional
    public URI updateNote(NoteRequest noteRequest) {
        Note persistedNote = noteRepository.save(noteRequest.updateNote());
        Iterable<Vote> persistedVote = voteRepository.saveAll(noteRequest.updateVote());
        NoteStyle noteStyle = noteStyleRepository.save(noteRequest.updateNoteStyle(persistedNote.getNoteId(), noteRequest.getStyle()));
        return buildURI(noteRequest.getRetroBoardId(), noteRequest.getWallId());
    }

    @Transactional
    public URI deleteNote(String noteId) {
        URI uri = null;
        Optional<Note> persistedNote = noteRepository.findById(noteId);
        if (persistedNote.isPresent()) {
            Note note = persistedNote.get();
            noteStyleRepository.deleteById(note.getNoteId());
            voteRepository.deleteByItemIdAndType(note.getNoteId(), String.valueOf(Vote.Type.NOTE));
            noteRepository.deleteById(note.getNoteId());
            uri = buildURI(note.getRetroBoardId(), note.getWallId());
        }
        return uri;
    }
}
