package com.boards.core.service;

import com.boards.core.model.Note;
import com.boards.core.model.RetroBoard;
import com.google.firebase.FirebaseApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BoardServiceImplTest {

    @Autowired
    private FirebaseApp firebaseApp;

    @Autowired
    private CacheManagerImpl cacheManager;

    private BoardServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BoardServiceImpl(firebaseApp, cacheManager);
    }

    @Test
    void it_should_get_retro_boards() throws IOException {
        List<RetroBoard> board = service.getRetroBoards();
        assertNotNull(board);
        assertFalse(board.isEmpty());
    }

    @Test
    void it_should_get_retro_board_from_cache() throws IOException {
        long startMillis = System.currentTimeMillis();
        List<RetroBoard> board = service.getRetroBoards();
        long totalTimeTaken = System.currentTimeMillis() - startMillis;

        assertTrue(totalTimeTaken < 1000);
        assertNotNull(board);
        assertFalse(board.isEmpty());
    }

    @Test
    void it_should_get_all_retro_walls() {
//        service.getRetroWall();
    }

    @Test
    void it_should_get_all_notes() throws IOException {
        List<Note> notes = service.getRetroNotes();
        assertFalse(notes.isEmpty());
    }

    @Test
    void it_should_get_all_notes_from_cache() throws IOException {
        long startMillis = System.currentTimeMillis();
        List<Note> notes = service.getRetroNotes();
        long totalTimeTaken = System.currentTimeMillis() - startMillis;

        assertTrue(totalTimeTaken < 1000);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
    }
}