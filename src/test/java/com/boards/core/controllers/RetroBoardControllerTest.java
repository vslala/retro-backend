package com.boards.core.controllers;

import com.boards.core.model.RetroBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetroBoardControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomPort;

    private String getTestUrl(String path) {
        return String.format("http://localhost:%d/%s", randomPort, path);
    }

    @Test
    void get_retro_board_by_id() {
        RetroBoard retroBoard = restTemplate.getForObject(getTestUrl("/retro-board/-M0QZpAXh5sQqawVkrrM"), RetroBoard.class);
        assertNotNull(retroBoard);
        assertEquals("-M0QZpAXh5sQqawVkrrM", retroBoard.getId());
    }

    @Test
    void get_all_retro_boards() {
        RetroBoard[] retroBoardList = restTemplate.getForObject(getTestUrl("/retro-board"), RetroBoard[].class);
        assertTrue(retroBoardList.length > 0);
    }
}