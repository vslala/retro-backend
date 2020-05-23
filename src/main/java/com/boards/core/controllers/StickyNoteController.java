package com.boards.core.controllers;

import com.boards.core.model.dto.CreateNoteRequest;
import com.boards.core.model.dto.NotesResponse;
import com.boards.core.services.StickyNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.boards.core.configuration.AppConfig.*;

@CrossOrigin(exposedHeaders = {EXPOSE_ACCESS_TOKEN, EXPOSE_LOCATION, EXPOSE_UID})
@RestController
@RequestMapping("/retro-board/walls/notes")
public class StickyNoteController {

    private StickyNoteService stickyNoteService;

    public StickyNoteController(StickyNoteService stickyNoteService) {
        this.stickyNoteService = stickyNoteService;
    }

    @PostMapping
    public ResponseEntity<URI> createNote(@RequestBody CreateNoteRequest noteRequest) {
        URI uri = stickyNoteService.createNoteForWall(noteRequest);
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
