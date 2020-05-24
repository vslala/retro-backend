package com.boards.core.controllers;

import com.boards.core.model.dto.CreateNoteRequest;
import com.boards.core.model.dto.NotesResponse;
import com.boards.core.services.StickyNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public ResponseEntity<URI> createNote(@RequestBody CreateNoteRequest noteRequest) {
        URI uri = stickyNoteService.createNoteForWall(noteRequest);
        this.simpMessagingTemplate.convertAndSend("/topic/notes/" + noteRequest.getRetroBoardId(), uri.toString());
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
}
