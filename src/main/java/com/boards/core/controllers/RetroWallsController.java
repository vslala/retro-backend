package com.boards.core.controllers;

import com.boards.core.model.RetroWall;
import com.boards.core.services.RetroWallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/retro-board")
public class RetroWallsController {

    private RetroWallsService retroWallsService;

    @Autowired
    public RetroWallsController(RetroWallsService retroWallsService) {
        this.retroWallsService = retroWallsService;
    }

    @PostMapping("/wall")
    public ResponseEntity<HttpStatus> createWalls(@RequestBody RetroWall[] input) {
        List<RetroWall> retroWalls = Arrays.asList(input);
        URI resourceUrl = retroWallsService.createWalls(retroWalls);
        return ResponseEntity.created(resourceUrl).build();

    }
}
