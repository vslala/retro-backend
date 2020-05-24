package com.boards.core.controllers;

import com.boards.core.model.dto.NoteRequest;
import com.boards.core.model.dto.NoteResponse;
import com.boards.core.model.dto.NotesResponse;
import com.boards.core.services.StickyNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.boards.core.configuration.AppConfig.*;

@CrossOrigin(exposedHeaders = {EXPOSE_ACCESS_TOKEN, EXPOSE_LOCATION, EXPOSE_UID})
@RestController
@RequestMapping("/retro-board/walls/notes")
public class StickyNoteController {

    private StickyNoteService stickyNoteService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public StickyNoteController(StickyNoteService stickyNoteService, SimpMessagingTemplate simpMessagingTemplate) {
        this.stickyNoteService = stickyNoteService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping
    public ResponseEntity<URI> createNote(@RequestBody NoteRequest noteRequest) {
        URI uri = stickyNoteService.createNoteForWall(noteRequest);
        sendToNotesTopic(uri, noteRequest.getRetroBoardId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<NotesResponse> getNotes(@RequestParam("retroBoardId") String retroBoardId, @RequestParam("wallId") String wallId) {
        NotesResponse notesResponse = stickyNoteService.getNotesForWall(wallId);
        return ResponseEntity.ok(notesResponse);
    }

    @GetMapping("/{retroBoardId}")
    public ResponseEntity<NotesResponse> getNotesForRetroBoard(@PathVariable String retroBoardId) {
        NotesResponse notesResponse = stickyNoteService.getNotesForBoard(retroBoardId);
        return ResponseEntity.ok(notesResponse);
    }

    @PutMapping
    public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteRequest noteRequest) {
        URI uri = stickyNoteService.updateNote(noteRequest);
        sendToNotesTopic(uri, noteRequest.getRetroBoardId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteNote(@RequestParam("noteId") String noteId,
                                                 @RequestParam("retroBoardId") String retroBoardId) {
        URI uri = stickyNoteService.deleteNote(noteId);
        sendToNotesTopic(uri, retroBoardId);
        return ResponseEntity.noContent().build();
    }

    private void sendToNotesTopic(URI uri, String retroBoardId) {
        this.simpMessagingTemplate.convertAndSend("/topic/notes/" + retroBoardId, uri.toString());
    }
}
