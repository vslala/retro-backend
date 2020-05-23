package com.boards.core.controllers;

import com.boards.core.model.dto.CreateResponse;
import com.boards.core.model.dto.CreateRetroBoardRequest;
import com.boards.core.model.entities.RetroBoard;
import com.boards.core.services.RetroBoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.net.URI.create;

@Log4j
@CrossOrigin(exposedHeaders = {"Access-Token", "Uid", "Location"})
@RestController
@RequestMapping("/retro-board")
public class RetroBoardController {

    private RetroBoardService retroBoardService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RetroBoardController(RetroBoardService retroBoardService, SimpMessagingTemplate simpMessagingTemplate) {
        this.retroBoardService = retroBoardService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createRetroBoard(@RequestBody CreateRetroBoardRequest retroBoard) {
        CreateResponse createResponse = retroBoardService.createRetroBoard(retroBoard);
        return ResponseEntity.created(create(createResponse.getResourceUrl())).body(createResponse);
    }

    @GetMapping("/{retroBoardId}")
    public ResponseEntity<RetroBoard> get(@PathVariable String retroBoardId) {
        Optional<RetroBoard> retroBoard = retroBoardService.getRetroBoard(retroBoardId);
        if (retroBoard.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(retroBoard.get());
    }

    @GetMapping
    public ResponseEntity<List<RetroBoard>> getBoards() {
        List<RetroBoard> retroBoards = retroBoardService.getRetroBoards();
        return ResponseEntity.ok(retroBoards);
    }
}
