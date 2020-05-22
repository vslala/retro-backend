package com.boards.core.controllers;

import com.boards.core.model.dto.CreateRetroWallsRequest;
import com.boards.core.model.entities.RetroWall;
import com.boards.core.services.RetroWallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.boards.core.configuration.AppConfig.*;

@CrossOrigin(origins = LOCAL_ORIGIN,
        exposedHeaders = {EXPOSE_LOCATION, EXPOSE_ACCESS_TOKEN, EXPOSE_UID})
@RestController
@RequestMapping("/retro-board")
public class RetroWallsController {

    private RetroWallsService retroWallsService;

    @Autowired
    public RetroWallsController(RetroWallsService retroWallsService) {
        this.retroWallsService = retroWallsService;
    }

    @PostMapping("/wall")
    public ResponseEntity<HttpStatus> createWalls(@RequestBody CreateRetroWallsRequest input) {

        URI resourceUrl = retroWallsService.createWalls(input);
        return ResponseEntity.created(resourceUrl).build();

    }
}
