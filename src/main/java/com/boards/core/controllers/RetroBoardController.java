package com.boards.core.controllers;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.dto.retroboard.CreateResponse;
import com.boards.core.model.dto.retroboard.RetroBoardRequest;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.boards.core.services.RetroBoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.boards.core.configuration.AppUtil.getLoggedInUser;
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
    public ResponseEntity<CreateResponse> createRetroBoard(@RequestBody RetroBoardRequest retroBoard) {
        CreateResponse createResponse = retroBoardService.createRetroBoard(retroBoard);
        return ResponseEntity.created(create(createResponse.getResourceUrl())).body(createResponse);
    }

    @GetMapping("/{retroBoardId}")
    public ResponseEntity<RetroBoard> get(@PathVariable String retroBoardId) {
        Optional<RetroBoard> retroBoard = retroBoardService.getRetroBoard(getLoggedInUser(), retroBoardId);
        if (retroBoard.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(retroBoard.get());
    }

    @GetMapping
    public ResponseEntity<List<RetroBoard>> getBoards() {
        List<RetroBoard> retroBoards = retroBoardService.getRetroBoards();
        return ResponseEntity.ok(retroBoards);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateRetroBoard(@RequestBody RetroBoardRequest retroBoardRequest) {
        URI uri = retroBoardService.updateRetroBoard(retroBoardRequest);
        this.simpMessagingTemplate.convertAndSend("/topic/retro-board/" + retroBoardRequest.getId(), uri.toString());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{retroBoardId}")
    public ResponseEntity<HttpStatus> deleteBoard(@PathVariable String retroBoardId) {
        retroBoardService.deleteBoard(AppUtil.getLoggedInUser(), retroBoardId);
        return ResponseEntity.noContent().build();
    }
}
